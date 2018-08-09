package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.exceptions.NotFoundException;
import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDaoInMemory implements MealsDao {
    private final ConcurrentMap<Integer, Meal> map = new ConcurrentHashMap<>();
    private static final AtomicInteger counter = new AtomicInteger();

    {
        counter.set(0);
    }

    @Override
    public Meal get(int id) {
        Meal meal = map.get(id);
        if (meal == null) {
            throw new NotFoundException(id);
        } else {
            return meal;
        }
    }

    @Override
    public void insert(Meal meal) {
        int id = counter.addAndGet(1);
        meal.setId(id);
        map.put(id, meal);
    }

    @Override
    public void update(int id, Meal meal) {
        if (map.replace(id, meal) == null) {
            throw new NotFoundException(id);
        }
    }

    @Override
    public void delete(int id) {
        if (map.remove(id) == null) {
            throw new NotFoundException(id);
        }
    }

    @Override
    public Collection<Meal> getAll() {
        return map.values();
    }
}
