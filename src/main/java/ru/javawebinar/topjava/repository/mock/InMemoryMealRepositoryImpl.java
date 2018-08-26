package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
        return getUserMealsMap(userId).values();
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

