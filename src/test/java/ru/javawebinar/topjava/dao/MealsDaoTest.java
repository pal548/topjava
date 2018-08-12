package ru.javawebinar.topjava.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


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

    @Ignore
    @Test
    public void insert() {

    }

    @Test
    public void update() {
        Meal meal = new Meal(LocalDateTime.of(2018, 8, 9, 12, 0), "Ланч", 1000);
        meal.setId(meal1.getId());
        mealsDao.update(meal.getId(), meal);
        assertEquals(meal, mealsDao.get(meal.getId()));
    }

    @Test
    public void delete() {
        mealsDao.delete(meal1.getId());
        Meal meal = mealsDao.get(meal1.getId());
        assertNull(meal);
    }

    @Test
    public void getAll() {
        Collection<Meal> col = mealsDao.getAll();
        assertEquals(meal1, mealsDao.get(meal1.getId()));
        assertEquals(meal2, mealsDao.get(meal2.getId()));
        assertEquals(meal3, mealsDao.get(meal3.getId()));
    }
}