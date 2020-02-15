package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.DataStorage;
import ru.javawebinar.topjava.storage.InMemoryDataStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private DataStorage storage = new InMemoryDataStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String operation = request.getParameter("operation");

        if (operation != null && (operation.equals("create") || operation.equals("update")))  {
            Meal meal = null;
                 if (operation.equals("create")) {
                     meal = new Meal(null, "Вкуснятина", 0);
                     log.debug("create new");
                 } else if (operation.equals("update")) {
                     meal = storage.read(Integer.valueOf(request.getParameter("id")));
                     log.debug("{}", "update ".concat(meal.getId().toString()));
                 }
//            log.debug("{}", meal.isNotExist() ? "create new" : "update ".concat(meal.getId().toString()));
                 request.setAttribute("meal", meal);
                 request.getRequestDispatcher("/mealUpdate.jsp").forward(request, response);
        } else  if (operation != null && operation.equals("delete")){
            String id = request.getParameter("id");
            log.debug("delete {}", id);
            storage.delete(Integer.valueOf(id));
            response.sendRedirect("meals");
        } else {
            log.debug("redirect to meals");
            request.setAttribute("meals",
                    MealsUtil.nonfilteredByStreams(storage.readAll().stream().collect(Collectors.toList()), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
//        response.sendRedirect("meals.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        storage.create(meal);
        response.sendRedirect("meals");
    }
}
