package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface DataStorage {
    Meal create(Meal meal);
    Meal read(int id);
//    Meal update(int id);
    void delete(int id);
    Collection<Meal> readAll();
}
