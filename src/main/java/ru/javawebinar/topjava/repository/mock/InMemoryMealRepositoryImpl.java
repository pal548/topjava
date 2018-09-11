package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((meal)-> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> mealsMap = repository.computeIfAbsent(userId, i -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> mealsMap = repository.get(userId);
        return mealsMap != null && (mealsMap.remove(id) != null);
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> mealsMap = repository.get(userId);
        return mealsMap == null ? null : mealsMap.get(id);
    }

    @Override
    public List<MealWithExceed> getAll(int userId, int calories) {
        return doGetAllWithExceeded(userId, LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX, calories);
    }

    @Override
    public List<MealWithExceed> getAllFiltered(int userId, LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2, int calories) {
        return doGetAllWithExceeded(userId, date1, date2, time1, time2, calories);
    }

    private List<MealWithExceed> doGetAllWithExceeded(int userId, LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2, int calories) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null) {
            return Collections.emptyList();
        } else {
            Collection<Meal> mealsInDates = map.values().stream()
                    .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), date1, date2))
                    .collect(Collectors.toList());
            return MealsUtil.getWithExceeded(mealsInDates, calories).stream()
                    .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), time1, time2))
                    .sorted(Comparator.comparing(MealWithExceed::getDateTime).reversed())
                    .collect(toList());
        }
    }

}

