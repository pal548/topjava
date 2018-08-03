package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MyBoolean;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final MyBoolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, MyBoolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
}
