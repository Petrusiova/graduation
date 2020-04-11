package graduation.graduationProject.web.user;

import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static testData.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private AdminRestController controller;

    @Test
    public void create() throws Exception {
        User newUser = getNew();
        User created = controller.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() throws Exception {
        thrown.expect(DataAccessException.class);
        controller.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void delete() throws Exception {
        thrown.expect(NotFoundException.class);
        controller.delete(USER_ID);
        controller.get(USER_ID);
    }

    @Test
    public void deletedNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        controller.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = controller.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        controller.get(1);
    }

    @Test
    public void getByMail() throws Exception {
        User user = controller.getByMail("user@yandex.ru");
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    public void update() throws Exception {
        User created = controller.create(getNew());
        created.setName("NewNameForTest");
        Integer newId = created.getId();
        controller.update(created, newId);
        Assert.assertEquals("NewNameForTest", controller.get(newId).getName());
    }

    @Test
    public void updateNotFound() {
        thrown.expect(IllegalArgumentException.class);
        User user = controller.create(getNew());
        Integer newId = user.getId();
        user.setId(-150);
        controller.update(user, newId);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = controller.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }
}