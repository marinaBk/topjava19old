package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static javax.swing.UIManager.put;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
       // MealsUtil.MEALS.forEach(s -> this.save (s, SecurityUtil.authUserId()));
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500,1),1);
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000,1),1);
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500,2),2);
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100,1),2);
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000,2),2);
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 2),2);
      save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410,2),2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if ( get(meal.getId(), userId) == null) {
            return null;
        }
        // handle case: update, but not present in storage
      //  Map<Integer, Meal> mealsMap = repository.computeIfAbsent(userId, m -> new ConcurrentHashMap<>());
        Map<Integer, Meal> mealsMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        mealsMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
     /*   Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != userId) {
            return false;
        }
        return repository.remove(id) != null;
      */
      //return repository.values().removeIf(k -> k.getUserId() == userId &&  k.getId() == id );
        Meal meal = get(id, userId);

        return meal != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer,Meal> meals = repository.get(userId);

        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {  //Collection<Meal> getAll(int userId)
        return repository.get(userId).values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

