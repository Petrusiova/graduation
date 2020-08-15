package repo;

import graduation.graduationProject.VoteTestData;
import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.repository.UserRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static graduation.graduationProject.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserRepoTest extends AbstractRepoTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private UserRepository repository;

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
                () -> repository.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    public void delete() throws Exception {
        int id = repository.save(getNew()).getId();
        Assertions.assertTrue(repository.delete(id));
        assertThrows(NotFoundException.class,
                () -> repository.get(id));
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

    @Test
    void getWithVotes() throws Exception {
        User user = repository.getWithVotes(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        VoteTestData.VOTE_MATCHER.assertMatch(user.getVotes(), VoteTestData.VOTE_1, VoteTestData.VOTE_3);
    }

    @Test
    void getWithVotesNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.getWithVotes(1));
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> repository.save(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> repository.save(new User(null, "User", "  ", "password", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> repository.save(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)), ConstraintViolationException.class);
    }

    @Test
    void enable() {
        repository.enable(USER_ID, false);
        assertFalse(repository.get(USER_ID).isEnabled());
        repository.enable(USER_ID, true);
        assertTrue(repository.get(USER_ID).isEnabled());
    }
}
