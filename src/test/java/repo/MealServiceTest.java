package repo;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.service.MealService;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static graduation.graduationProject.MealTestData.*;
import static graduation.graduationProject.RestaurantTestData.ASTORIA_ID;
import static graduation.graduationProject.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, ASTORIA_ID);

        Integer newId = created.getId();
        newMeal.setId(newId);

        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(ASTORIA_ID, newId), newMeal);
    }

    @Test
    public void duplicateMealCreate() throws Exception {
        assertThrows(DataAccessException.class,
                () -> service.create(new Meal(MEAL_1.getDate(), MEAL_1.getDescription(), MEAL_1.getPrice()), ASTORIA_ID));
    }

    @Test
    public void delete() throws Exception {
        Assertions.assertTrue(service.delete(MEAL_1_ID, ASTORIA_ID));
        assertThrows(NotFoundException.class,
                () -> service.get(MEAL_1_ID, ASTORIA_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.delete(1, ASTORIA_ID));
    }

    @Test
    public void get() throws Exception {
        Meal Meal = service.get(ASTORIA_ID, MEAL_1_ID);
        MEAL_MATCHER.assertMatch(Meal, MEAL_1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.get(1, ASTORIA_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, ASTORIA_ID);
        MEAL_MATCHER.assertMatch(service.get(ASTORIA_ID, MEAL_1_ID), updated);
    }

    @Test
    public void getMealByRestaurantToday() throws Exception {
        List<Meal> all = service.getMealByRestaurantToday(100004);
        MEAL_MATCHER.assertMatch(all, Collections.singletonList(MEAL_4));
    }


    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Meal(null, LocalDate.of(2015, Month.JUNE, 1), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal(null, LocalDate.of(2015, Month.JUNE, 1), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal(null, LocalDate.of(2015, Month.JUNE, 1), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}
