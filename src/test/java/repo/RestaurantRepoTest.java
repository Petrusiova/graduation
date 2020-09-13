package repo;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static graduation.graduationProject.MealTestData.MEAL_1;
import static graduation.graduationProject.MealTestData.MEAL_MATCHER;
import static graduation.graduationProject.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;


public class RestaurantRepoTest extends AbstractRepoTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private RestaurantRepository repository;

    @Test
    public void create() throws Exception {
        Restaurant newRest = getNew();
        Restaurant created = repository.save(newRest);

        Integer newId = created.getId();
        newRest.setId(newId);

        REST_MATCHER.assertMatch(created, newRest);
        REST_MATCHER.assertMatch(repository.get(newId), newRest);
    }

    @Test
    public void deleteSingleRestaurant() throws Exception {
        Restaurant r = repository.save(new Restaurant("FANAGORIA"));
        int id = r.getId();
        Assertions.assertTrue(repository.delete(id));
        assertThrows(NotFoundException.class,
                () -> repository.get(id));
    }

    @Test
    public void deleteRestaurantWithRelations() throws Exception {
        assertThrows(DataIntegrityViolationException.class,
                () -> repository.delete(ASTORIA_ID));
    }

    @Test
    public void get() throws Exception {
        Restaurant astoria = repository.get(ASTORIA_ID);
        REST_MATCHER.assertMatch(astoria, ASTORIA);
    }

    @Test
    public void getByName() throws Exception {
        Restaurant astoria = repository.getByName("ASTORIA");
        REST_MATCHER.assertMatch(astoria, ASTORIA);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.get(1));
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        repository.save(updated);
        REST_MATCHER.assertMatch(repository.get(ASTORIA_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = repository.getAll();
        REST_MATCHER.assertMatch(all, ASTORIA, TIFFANY, VICTORIA);
    }

//    @Test
//    void getWithMeals() throws Exception {
//        Restaurant r = repository.getWithMeals(ASTORIA_ID);
//        REST_MATCHER.assertMatch(r, ASTORIA);
//        MEAL_MATCHER.assertMatch(r.getMeals(), MEAL_1);
//    }
//
//    @Test
//    void getWithMealsNotFound() throws Exception {
//        Assertions.assertThrows(NotFoundException.class,
//                () -> repository.getWithMeals(1));
//    }

//    @Test
//    void getWithVotes() throws Exception {
//        Restaurant r = repository.getWithVotesCount(ASTORIA_ID);
//        REST_MATCHER.assertMatch(r, ASTORIA);
//        VoteTestData.VOTE_MATCHER.assertMatch(r.getVotes(), VoteTestData.VOTE_1, VoteTestData.VOTE_2);
//    }

//    @Test
//    void getWithVotesNotFound() throws Exception {
//        Assertions.assertThrows(NotFoundException.class,
//                () -> repository.getWithVotesCountToday(123));
//    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> repository.save(new Restaurant(null, "  ")), ConstraintViolationException.class);
    }

    @Test
    void enable() {
        repository.enable(ASTORIA_ID, false);
        assertFalse(repository.get(ASTORIA_ID).isEnabled());
        repository.enable(ASTORIA_ID, true);
        assertTrue(repository.get(ASTORIA_ID).isEnabled());
    }
}
