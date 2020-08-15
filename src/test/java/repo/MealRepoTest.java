package repo;

import graduation.graduationProject.RestaurantTestData;
import graduation.graduationProject.model.Meal;
import graduation.graduationProject.repository.MealRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static graduation.graduationProject.MealTestData.*;
import static graduation.graduationProject.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MealRepoTest extends AbstractRepoTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MealRepository repository;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = repository.save(newMeal, MEAL_1_RESTAURANT_ID);

        Integer newId = created.getId();
        newMeal.setId(newId);

        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.get(newId), newMeal);
    }

    @Test
    public void duplicateMealCreate() throws Exception {
        assertThrows(DataAccessException.class,
                () -> repository.save(new Meal(MEAL_1.getDate(), MEAL_1.getDescription(), MEAL_1.getPrice()), MEAL_1_RESTAURANT_ID));
    }

    @Test
    public void delete() throws Exception {
        Assertions.assertTrue(repository.delete(MEAL_1_ID));
        assertThrows(NotFoundException.class,
                () -> repository.get(MEAL_1_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.delete(1));
    }

    @Test
    public void get() throws Exception {
        Meal Meal = repository.get(MEAL_1_ID);
        MEAL_MATCHER.assertMatch(Meal, MEAL_1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.get(1));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        repository.save(updated, MEAL_1_RESTAURANT_ID);
        MEAL_MATCHER.assertMatch(repository.get(MEAL_1_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = repository.getAll();
        MEAL_MATCHER.assertMatch(all, MEALS);
    }

    @Test
    public void getAllByDate() throws Exception {
        List<Meal> all = repository.getAllByDate(LocalDate.of(2020, 4, 6));
        MEAL_MATCHER.assertMatch(all, ALL_MEALS_EQL_DATE);
    }

    @Test
    public void getAllByDateNotExists() throws Exception {
        List<Meal> all = repository.getAllByDate(LocalDate.now());
        assertTrue(all.isEmpty());
    }

    @Test
    public void getAllByRestaurant() throws Exception {
        List<Meal> all = repository.getAllByRestaurant(MEAL_1_RESTAURANT_ID);
        MEAL_MATCHER.assertMatch(all, MEAL_1);
    }

    @Test
    public void getAllByRestaurantNotExists() throws Exception {
        List<Meal> all = repository.getAllByRestaurant(MEAL_1_RESTAURANT_ID - 100);
        assertTrue(all.isEmpty());
    }

    @Test
    public void getAllByRestaurantAndDate() throws Exception {
        List<Meal> all = repository.getAllByRestaurantAndDate(
                MEAL_1_RESTAURANT_ID, LocalDate.of(2020, 4, 6));
        MEAL_MATCHER.assertMatch(all, MEAL_1);
    }

    @Test
    void getWithVotes() throws Exception {
        Meal meal = repository.getWithRestaurant(MEAL_1_ID, RestaurantTestData.ASTORIA_ID);
        MEAL_MATCHER.assertMatch(meal, MEAL_1);
        RestaurantTestData.REST_MATCHER.assertMatch(meal.getRestaurant(), RestaurantTestData.ASTORIA);
    }

    @Test
    void getWithVotesNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> repository.getWithRestaurant(MEAL_1_ID, 2));
    }


    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> repository.save(new Meal(null, LocalDate.of(2015, Month.JUNE, 1), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> repository.save(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> repository.save(new Meal(null, LocalDate.of(2015, Month.JUNE, 1), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> repository.save(new Meal(null, LocalDate.of(2015, Month.JUNE, 1), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}
