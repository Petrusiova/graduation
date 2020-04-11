package graduation.graduationProject.web.menu;

import graduation.graduationProject.model.Menu;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.Assert;
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

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static testData.MenuControllerTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuControllerTest {

    @Autowired
    private MenuController controller;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAll() {
        MENU_MATCHER.assertMatch(controller.getAll(), getAllMenu());
    }

    @Test
    public void get() {
        MENU_MATCHER.assertMatch(controller.get(MENU_1.getId()), MENU_1);
    }

    @Test
    public void getByRestaurant() {
        MENU_MATCHER.assertMatch(controller.getByRestaurant(100003),
                Stream.of(MENU_2, MENU_3)
                        .sorted(Comparator.comparing(Menu::getDate).reversed())
                        .collect(Collectors.toList()));
    }

    @Test
    public void getByDate() {
        MENU_MATCHER.assertMatch(controller.getByDate(LocalDate.of(2020, 4, 6)), getAllMenu());
    }

    @Test
    public void getByRestaurantAndDate() {
        MENU_MATCHER.assertMatch(controller.getByRestaurantAndDate(100003, LocalDate.of(2020, 4, 6)),
                Stream.of(MENU_2, MENU_3)
                        .sorted(Comparator.comparing(Menu::getDate).reversed())
                        .collect(Collectors.toList()));
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        controller.get(-150);
    }

    @Test
    public void create() {
        Menu currentMenu = new Menu(100004, "tea", 5.0);
        Menu newMenu = controller.create(currentMenu);
        Integer newId = newMenu.getId();
        currentMenu.setId(newId);
        MENU_MATCHER.assertMatch(newMenu, currentMenu);
        MENU_MATCHER.assertMatch(controller.get(newId), currentMenu);
    }

    @Test
    public void createIllegal() {
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage("could not execute statement");
        Menu currentMenu = new Menu(-150, "tea", 5.0);
        controller.create(currentMenu);
    }

    @Test
    public void delete() {
        thrown.expect(NotFoundException.class);
        Menu currentMenu = new Menu(100004, "tea", 5.0);
        Menu newMenu = controller.create(currentMenu);
        Integer newId = newMenu.getId();
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
        Menu currentMenu = new Menu(100004, "tea", 5.0);
        Menu newMenu = controller.create(currentMenu);
        Integer newId = newMenu.getId();
        newMenu.setMeal("newMeal");
        controller.update(newMenu, newId);
        Assert.assertEquals("newMeal", controller.get(newId).getMeal());
    }

    @Test
    public void updateNotFound() {
        thrown.expect(IllegalArgumentException.class);
        Menu newMenu = controller.create(new Menu(100004, "tea", 5.0));
        controller.update(newMenu, newMenu.getId()+1);
    }
}