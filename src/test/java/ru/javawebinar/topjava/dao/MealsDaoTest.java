package ru.javawebinar.topjava.dao;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class MealsDaoTest {
    private MealsDao mealsDao;
    private Meal meal1;
    private Meal meal2;
    private Meal meal3;

    {
        meal1 = new Meal(0, LocalDateTime.of(2018, 8, 8, 9, 0), "Завтрак", 500 );
        meal2 = new Meal(0, LocalDateTime.of(2018, 8, 8, 14, 0), "Обед", 1000 );
        meal3 = new Meal(0, LocalDateTime.of(2018, 8, 8, 19, 0), "Ужин", 500 );
    }

    public MealsDaoTest(MealsDao mealsDao) {
        this.mealsDao = mealsDao;
    }

    @Before
    public void setUp() throws Exception {
        mealsDao.save(meal1);
        mealsDao.save(meal2);
        mealsDao.save(meal3);
    }

    @Test
    public void get() {
        Meal meal = mealsDao.get(meal1.getId());
        assertEquals(meal1, meal);
    }

    @Test(expected = NotFo)
    public void save() {

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {

    }

    @Test
    public void getAll() {
    }
}