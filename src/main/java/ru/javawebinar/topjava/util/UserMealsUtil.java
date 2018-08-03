package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(21,0), 2000);
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(21,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        Map<LocalDate, MyBoolean> map_exceed = new HashMap<>();
        for (UserMeal m : mealList) {
            LocalDate day = LocalDate.of(m.getDateTime().getYear(), m.getDateTime().getMonth(), m.getDateTime().getDayOfMonth());
            map.merge(day, m.getCalories(), (o, n) -> o + n);

            map_exceed.putIfAbsent(day, new MyBoolean());
            MyBoolean b = map_exceed.get(day);
            b.setValue(map.get(day) > caloriesPerDay);
            result.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), b));
        }
        return result;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result;

        Map<LocalDate, Integer> map = mealList.stream()
                .collect(Collectors.groupingBy(m -> LocalDate.of(m.getDateTime().getYear(), m.getDateTime().getMonth(), m.getDateTime().getDayOfMonth()),
                        Collectors.summingInt(UserMeal::getCalories)));
        result = mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> {
                    LocalDate day = LocalDate.of(m.getDateTime().getYear(), m.getDateTime().getMonth(), m.getDateTime().getDayOfMonth());
                    return new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), new MyBoolean(map.get(day) > caloriesPerDay));})
                .collect(Collectors.toList());

        return result;
    }



}
