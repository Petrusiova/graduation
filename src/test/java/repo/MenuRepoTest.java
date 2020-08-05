package repo;

import graduation.graduationProject.model.Menu;
import graduation.graduationProject.repository.MenuRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static graduation.graduationProject.MenuTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MenuRepoTest extends AbstractRepoTest {


    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MenuRepository repository;

    //    @Autowired
//    private CacheManager cacheManager;

//    @Before
//    public void setUp() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

    @Test
    public void create() throws Exception {
        Menu newMenu = getNew();
        Menu created = repository.save(newMenu, MENU_1_RESTAURANT_ID);
        Integer newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(repository.get(newId), newMenu);
    }

    @Test
    public void duplicateMenuCreate() throws Exception {
        assertThrows(DataAccessException.class,
                () -> repository.save(new Menu(MENU_1.getMeal(), new Random().nextInt(), MENU_1.getDate()), MENU_1_RESTAURANT_ID));
    }

    @Test
    public void delete() throws Exception {
        Assertions.assertTrue(repository.delete(MENU_1_ID));
        assertThrows(NotFoundException.class,
                () -> repository.get(MENU_1_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.delete(1));
    }

    @Test
    public void get() throws Exception {
        Menu menu = repository.get(MENU_1_ID);
        MENU_MATCHER.assertMatch(menu, MENU_1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.get(1));
    }

    @Test
    public void update() throws Exception {
        Menu updated = getUpdated();
        repository.save(updated, MENU_1_RESTAURANT_ID);
        MENU_MATCHER.assertMatch(repository.get(MENU_1_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Menu> all = repository.getAll();
        MENU_MATCHER.assertMatch(all, ALL_MENU);
    }

    @Test
    public void getAllByDate() throws Exception {
        List<Menu> all = repository.getAllByDate(MENU_DATE);
        MENU_MATCHER.assertMatch(all, ALL_MENU_EQL_DATE);
    }

    @Test
    public void getAllByDateNotExists() throws Exception {
        List<Menu> all = repository.getAllByDate(LocalDate.now());
        assertTrue(all.isEmpty());
    }

    @Test
    public void getAllByRestaurant() throws Exception {
        List<Menu> all = repository.getAllByRestaurant(MENU_1_RESTAURANT_ID);
        MENU_MATCHER.assertMatch(all, MENU_1);
    }

    @Test
    public void getAllByRestaurantNotExists() throws Exception {
        List<Menu> all = repository.getAllByRestaurant(MENU_1_RESTAURANT_ID - 100);
        assertTrue(all.isEmpty());
    }

    @Test
    public void getAllByRestaurantAndDate() throws Exception {
        List<Menu> all = repository.getAllByRestaurantAndDate(MENU_1_RESTAURANT_ID, MENU_DATE);
        MENU_MATCHER.assertMatch(all, MENU_1);
    }

    //    @Test
//    void getWithUser() throws Exception {
//        Meal adminMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
//        MEAL_MATCHER.assertMatch(adminMeal, ADMIN_MEAL1);
//        UserTestData.USER_MATCHER.assertMatch(adminMeal.getUser(), UserTestData.ADMIN);
//    }
//
//    @Test
//    void getWithUserNotFound() throws Exception {
//        Assertions.assertThrows(NotFoundException.class,
//                () -> service.getWithUser(1, ADMIN_ID));
//    }
}
