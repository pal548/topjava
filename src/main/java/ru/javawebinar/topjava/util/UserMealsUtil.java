package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(21,0), 2000);
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(21,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new LinkedList<>();
        for (UserMeal meal : mealList) {
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                int sum = 0;
                for(UserMeal meal2 : mealList){
                    if (TimeUtil.isBetween(meal2.getDateTime().toLocalTime(), startTime, endTime)
                        && meal2.getDateTime().toLocalDate().equals(meal.getDateTime().toLocalDate()) ) {
                        sum += meal2.getCalories();
                    }
                }
                boolean exceeded = sum > caloriesPerDay;
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded));
            }

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
                    return new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), map.get(day) > caloriesPerDay);})
                .collect(Collectors.toList());

        return result;
    }



}
