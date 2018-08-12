package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDaoInMemory implements MealsDao {
    private final ConcurrentMap<Integer, Meal> map = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Meal meal) {
        int id = counter.addAndGet(1);
        meal.setId(id);
        map.put(id, meal);
    }

    @Override
    public void update(int id, Meal meal) {
        map.replace(id, meal);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return map.values();
    }
}
