package repo;


import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.service.RestaurantService;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static graduation.graduationProject.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private RestaurantService service;

    @Test
    public void create() throws Exception {
        Restaurant newRest = getNew();
        Restaurant created = service.create(newRest);

        Integer newId = created.getId();
        newRest.setId(newId);

        REST_MATCHER.assertMatch(created, newRest);
        REST_MATCHER.assertMatch(service.get(newId), newRest);
    }

    @Test
    public void deleteSingleRestaurant() throws Exception {
        Restaurant r = service.create(new Restaurant("FANAGORIA"));
        int id = r.getId();
        assertTrue(service.delete(id));
        assertThrows(NotFoundException.class,
                () -> service.get(id));
    }

    @Test
    public void deleteRestaurantWithRelations() throws Exception {
        assertThrows(DataIntegrityViolationException.class,
                () -> service.delete(ASTORIA_ID));
    }

    @Test
    public void get() throws Exception {
        Restaurant astoria = service.get(ASTORIA_ID);
        REST_MATCHER.assertMatch(astoria, ASTORIA);
    }

    @Test
    public void getByName() throws Exception {
        Restaurant astoria = service.getByName("ASTORIA");
        REST_MATCHER.assertMatch(astoria, ASTORIA);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.get(1));
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        service.update(updated);
        REST_MATCHER.assertMatch(service.get(ASTORIA_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        REST_MATCHER.assertMatch(all, ASTORIA, TIFFANY, VICTORIA);
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Restaurant(null, "  ")), ConstraintViolationException.class);
    }

    @Test
    void enable() {
        service.enable(ASTORIA_ID, false);
        assertFalse(service.get(ASTORIA_ID).isEnabled());
        service.enable(ASTORIA_ID, true);
        assertTrue(service.get(ASTORIA_ID).isEnabled());
    }
}
