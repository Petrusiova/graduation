package repo;

import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.repository.MenuRepository;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.repository.UserRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import repo.AbstractRepoTest;

import java.util.List;

import static graduation.graduationProject.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserRepoTest extends AbstractRepoTest {


    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private UserRepository repository;

    //    @Autowired
//    private CacheManager cacheManager;

//    @Before
//    public void setUp() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

    @Test
    public void create() throws Exception {
        User newUser = getNew();
        User created = repository.save(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class,
                () -> repository.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    public void delete() throws Exception {
        Assertions.assertTrue(repository.delete(USER_ID));
        assertThrows(NotFoundException.class,
                () -> repository.get(USER_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.delete(1));
    }

    @Test
    public void get() throws Exception {
        User user = repository.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.get(1));
    }

    @Test
    public void getByEmail() throws Exception {
        User user = repository.getByEmail("user@yandex.ru");
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    public void update() throws Exception {
        User updated = getUpdated();
        repository.save(updated);
        USER_MATCHER.assertMatch(repository.get(USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = repository.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }
}
