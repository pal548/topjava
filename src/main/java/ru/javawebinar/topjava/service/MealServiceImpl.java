package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithIdOfUserId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2) {
        return repository.getAllFiltered(userId, date1, date2, time1, time2);
    }

    @Override
    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException {
        return checkNotFoundWithIdOfUserId(repository.get(userId, id), id, userId);
    }

    @Override
    public void update(int userId, Meal meal) throws NotFoundException {
        checkNotFoundWithIdOfUserId(repository.save(userId, meal), meal.getId(), userId);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        checkNotFoundWithIdOfUserId(repository.delete(userId, id), id, userId);
    }


}