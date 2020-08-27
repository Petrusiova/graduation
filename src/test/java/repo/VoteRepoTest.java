package repo;

import graduation.graduationProject.RestaurantTestData;
import graduation.graduationProject.UserTestData;
import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.model.Vote;
import graduation.graduationProject.repository.VoteRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.RestaurantTestData.ASTORIA;
import static graduation.graduationProject.RestaurantTestData.ASTORIA_ID;
import static graduation.graduationProject.UserTestData.ADMIN_ID;
import static graduation.graduationProject.UserTestData.USER_ID;
import static graduation.graduationProject.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class VoteRepoTest extends AbstractRepoTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private VoteRepository repository;

    @Test
    public void create() throws Exception {
        Vote newVote = new Vote();
        Vote created = repository.save(newVote, USER_ID, ASTORIA_ID);

        Integer newId = created.getId();
        newVote.setId(newId);

        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.get(newId, USER_ID), newVote);
    }

    @Test
    public void delete() throws Exception {
        Assertions.assertTrue(repository.delete(VOTE_1_ID, USER_ID));
        assertThrows(NotFoundException.class,
                () -> repository.get(VOTE_1_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.delete(1, USER_ID));
    }

    @Test
    public void get() throws Exception {
        Vote vote = repository.get(VOTE_1_ID, USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    public void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.get(VOTE_1_ID, ADMIN_ID));
    }


    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.get(1, ADMIN_ID));
    }

    @Test
    public void update() throws Exception {
        Vote updated = getUpdated();
        Vote created = repository.save(updated, USER_ID, 100004);
        VOTE_MATCHER.assertMatch(repository.get(created.getId(), USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Vote> all = repository.getAll(USER_ID);
        VOTE_MATCHER.assertMatch(all, VOTE_1, VOTE_2);
    }

    @Test
    public void getWithRestaurant() throws Exception {
        Vote vote = repository.getWithRestaurant(VOTE_1_ID, ASTORIA_ID, USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
        RestaurantTestData.REST_MATCHER.assertMatch(vote.getRestaurant(), ASTORIA);
    }

    @Test
    public void getWithUser() throws Exception {
        Vote vote = repository.getWithUser(VOTE_1_ID, USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
        UserTestData.USER_MATCHER.assertMatch(vote.getUser(), UserTestData.USER);
    }

    @Test
    public void getWithNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> repository.getWithUser(VOTE_1_ID, USER_ID - 159));
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> repository.save(new Vote(null, null), USER_ID, ASTORIA_ID), ConstraintViolationException.class);
    }
}
