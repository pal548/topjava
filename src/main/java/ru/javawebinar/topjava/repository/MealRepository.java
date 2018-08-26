package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAllFiltered(int userId, LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2);
}
