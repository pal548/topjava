package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
            return mealsMap.computeIfAbsent(counter.incrementAndGet(), k -> {meal.setId(k);
                                                                             return meal;});
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
        LocalDate date11 = date1 == null ? LocalDate.MIN : date1;
        LocalDate date22 = date2 == null ? LocalDate.MAX : date2;
        LocalTime time11 = time1 == null ? LocalTime.MIN : time1;
        LocalTime time22 = time2 == null ? LocalTime.MAX : time2;
        return doGetAllWithExceeded(userId, date11, date22, time11, time22, calories);
    }

    private Map<Integer, Meal> getUserMealsMap(int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            repository.put(userId, map);
        }
        return map;
    }

    private List<MealWithExceed> doGetAllWithExceeded(int userId, LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2, int calories) {
        return MealsUtil.getWithExceeded(getUserMealsMap(userId).values(), calories).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), time1, time2)
                        && DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), date1, date2))
                .sorted(Comparator.comparing(MealWithExceed::getDateTime).reversed())
                .collect(toList());
    }

}

