package ru.javawebinar.topjava.dao;

public class MealsDaoTestInMemory extends MealsDaoTest {
    public MealsDaoTestInMemory() {
        super(new MealsDaoInMemory());
    }
}
