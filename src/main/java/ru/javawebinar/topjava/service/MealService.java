package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    List<Meal> getAllFiltered(int userId,
                              LocalDate date1, LocalDate date2,
                              LocalTime time1, LocalTime time2);

    Meal create(int userId, Meal meal);

    Meal get(int userId, int id) throws NotFoundException;

    void update(int userId, Meal meal) throws NotFoundException;

    void delete(int userId, int id) throws NotFoundException;

}
