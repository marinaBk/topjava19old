package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryDataStorage implements DataStorage {
    //private  ConcurrentHashMap<Integer, Meal> mealsHashMap = new ConcurrentHashMap<>();
    Map<Integer, Meal> mealsHashMap = new ConcurrentHashMap<> ();
    private AtomicInteger currentID = new AtomicInteger(0);

     {
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
         create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal create(Meal meal) {
       if (Objects.nonNull(meal)) {
         if (meal.isNotExist() || meal.getId() > currentID.get() || meal.getId() <=0) {
                meal.setId(currentID.incrementAndGet());

         }
           return mealsHashMap.put(meal.getId(),meal);
       }
        return null;
    }

    @Override
    public Meal read(int id) {
        return mealsHashMap.get(id);
    }
/*
    @Override
    public Meal update(int id) {
        return mealsHashMap.get(id);
    }
    */

    @Override
    public void delete(int id) {
        mealsHashMap.remove(id);

    }

    @Override
    public Collection<Meal> readAll() {
        return mealsHashMap.values();
    }
/*
    public Map<Integer, Meal> getMealsHashMap() {
        return mealsHashMap;
    }

 */
}
