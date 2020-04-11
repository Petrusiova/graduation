package graduation.graduationProject.web.restaurant;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static testData.RestaurantControllerTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantControllerTest {

    @Autowired
    private RestaurantController controller;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAll() {
        RESTAURANT_MATCHER.assertMatch(controller.getAll(), getAllRestaurants());
    }

    @Test
    public void get() {
        RESTAURANT_MATCHER.assertMatch(controller.get(ASTORIA.getId()), ASTORIA);
    }

    @Test
    public void getByName() {
        RESTAURANT_MATCHER.assertMatch(controller.getByName("ASTORIA"), ASTORIA);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        controller.get(-150);
    }

    @Test
    public void create() {
        Restaurant newRestaurant = controller.create("Fanagoria");
        Integer newId = newRestaurant.getId();
        RESTAURANT_MATCHER.assertMatch(controller.get(newId), newRestaurant);
    }

    @Test
    public void createIllegal() {
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage("could not execute statement");
        controller.create("ASTORIA");
    }

    @Test
    public void delete() {
        thrown.expect(NotFoundException.class);
        Restaurant newRestaurant = controller.create("Fanagoria");
        Integer newId = newRestaurant.getId();
        controller.delete(newId);
        controller.get(newId);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        controller.delete(-150);
    }

    @Test
    public void update() {
        Restaurant newRestaurant = controller.create("Fanagoria");
        Integer newId = newRestaurant.getId();
        newRestaurant.setName("Nigeria");
        controller.update(newRestaurant, newId);
        RESTAURANT_MATCHER.assertMatch(controller.get(newId), newRestaurant);
    }

    @Test
    public void updateNotFound() {
        thrown.expect(IllegalArgumentException.class);
        Restaurant newRestaurant = controller.create("Fanagoria");
        Integer newId = newRestaurant.getId();
        newRestaurant.setId(-150);
        controller.update(newRestaurant, newId);
    }
}