package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MealServlet extends HttpServlet {
    public static final String MEALS = "meals";
    public static final String MEALS_LIST_JSP = "/WEB-INF/jsp/meals/list.jsp";
    public static final String MEALS_EDIT_JSP = "/WEB-INF/jsp/meals/add-edit.jsp";
    private MealsDao mealsDao = new MealsDaoInMemory();

    {
        mealsDao.insert(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealsDao.insert(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealsDao.insert(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealsDao.insert(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealsDao.insert(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealsDao.insert(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String action = request.getParameter("action");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(request.getParameter("date")),
                LocalTime.parse(request.getParameter("time")));
        String description = request.getParameter("description");
        int cal = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, cal);
        if (action.equals("add")) {
            mealsDao.insert(meal);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            mealsDao.update(id, meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(mealsDao.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
            meals.sort(Comparator.comparing(MealWithExceed::getDateTime));
            request.setAttribute("meals", meals);
            request.getRequestDispatcher(MEALS_LIST_JSP).forward(request, response);
            return;
        }
        int id = 0;
        if (!action.equals("add")) {
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                response.sendRedirect(MEALS);
                return;
            }
        }
        switch (action) {
            case "delete":
                mealsDao.delete(id);
                response.sendRedirect(MEALS);
                break;
            case "edit":
            case "add":
                if (action.equals("edit")) {
                    Meal meal = mealsDao.get(id);
                    request.setAttribute("meal", meal);
                }
                request.getRequestDispatcher(MEALS_EDIT_JSP).forward(request, response);
                break;
        }

    }
}
