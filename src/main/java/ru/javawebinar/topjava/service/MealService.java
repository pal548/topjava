package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public interface MealService {
    Collection<MealWithExceed> getAllWithExceeded(int userId, int caloriesPerDay);

    Meal create(int userId, Meal meal);

    Meal get(int userId, int id) throws NotFoundException;

    void update(int userId, Meal meal) throws NotFoundException;

    void delete(int userId, int id) throws NotFoundException;

}
