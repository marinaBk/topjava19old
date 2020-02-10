package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
         /* return filtered list with correctly exceeded field
           version 1.0
        */

        List<UserMealWithExcess> listWithExceed = new ArrayList<>();
        Map<LocalDate, Integer> dates = new HashMap<>();

        for (UserMeal el : meals) {
            Integer caloriesSum = dates.getOrDefault(el.getDateTime().toLocalDate(), 0);
            dates.put(el.getDateTime().toLocalDate(), caloriesSum + el.getCalories());
            LocalTime localTime = el.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenInclusive(localTime, startTime, endTime)) {
                listWithExceed.add(new UserMealWithExcess(el.getDateTime(), el.getDescription(), el.getCalories(), false));
            }
        }
        try {
            for (UserMealWithExcess el : listWithExceed) {
                //reflection is used because the exceed field is private final :(
                Field nameField = el.getClass().getDeclaredField("exceed");
                nameField.setAccessible(true);
                //public function getDateTime() is inserted into UserMealWithExceed class in order to get a value of a date :(
               nameField.set(el, dates.get(el.getDateTime().toLocalDate()) > caloriesPerDay);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return listWithExceed;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
