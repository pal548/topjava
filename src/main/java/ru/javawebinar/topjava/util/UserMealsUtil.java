package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(21,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        int sum = 0, day = 0;
        boolean exceeded = false;
        List<Integer> foundIds = new LinkedList<>();
        List<UserMealWithExceed> result = new LinkedList<>();
        for (int i = 0; i < mealList.size(); i++) {
            UserMeal meal = mealList.get(i);
            if(meal.getDateTime().getDayOfMonth() != day) {
                day = meal.getDateTime().getDayOfMonth();
                newDay(mealList, result, foundIds, exceeded);
                exceeded = false;
                foundIds.clear();
                sum = 0;
            }
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                sum += meal.getCalories();
                exceeded = sum > caloriesPerDay;
                foundIds.add(i);
            }

        }
        newDay(mealList, result, foundIds, exceeded);
        return result;
    }

    private static void newDay(List<UserMeal> mealList, List<UserMealWithExceed> foundList, List<Integer> foundNewIds, boolean exceeded) {
        for(int id  : foundNewIds) {
            UserMeal meal = mealList.get(id);
            foundList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded));
        }
    }


}
