package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
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
        Map<Integer, Meal> mealsMap = getUserMealsMap(userId);
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
        Map<Integer, Meal> mealsMap = getUserMealsMap(userId);
        return mealsMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        return getUserMealsMap(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllFiltered(userId, null, null, null, null);
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2) {
        LocalDate date11 = date1 == null ? LocalDate.MIN : date1;
        LocalDate date22 = date2 == null ? LocalDate.MAX : date2;
        LocalTime time11 = time1 == null ? LocalTime.MIN : time1;
        LocalTime time22 = time2 == null ? LocalTime.MAX : time2;
        return getUserMealsMap(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), time11, time22)
                                && DateTimeUtil.isBetween(meal.getDate(), date11, date22))
                .collect(toList());
    }

    private Map<Integer, Meal> getUserMealsMap(int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            repository.put(userId, map);
        }
        return map;
    }
}

