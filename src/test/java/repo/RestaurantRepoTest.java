package repo;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static graduation.graduationProject.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RestaurantRepoTest extends AbstractRepoTest {


    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private RestaurantRepository repository;

    //    @Autowired
//    private CacheManager cacheManager;

//    @Before
//    public void setUp() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

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
    public void delete() throws Exception {
        Assertions.assertTrue(repository.delete(ASTORIA_ID));
        assertThrows(NotFoundException.class,
                () -> repository.get(ASTORIA_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.delete(1));
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
}
