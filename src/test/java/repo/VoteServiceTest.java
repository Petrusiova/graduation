package repo;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.service.VoteService;
import graduation.graduationProject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static graduation.graduationProject.RestaurantTestData.ASTORIA_ID;
import static graduation.graduationProject.UserTestData.ADMIN_ID;
import static graduation.graduationProject.UserTestData.USER_ID;
import static graduation.graduationProject.VoteTestData.VOTE_4;
import static graduation.graduationProject.VoteTestData.VOTE_MATCHER;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private VoteService service;

    @Test
    public void create() throws Exception {
        Vote newVote = new Vote();
        Vote created = service.create(ADMIN_ID, ASTORIA_ID);

        Integer newId = created.getId();
        newVote.setId(newId);

        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(ADMIN_ID), newVote);
    }

    @Test
    public void delete() throws Exception {
        Vote created = service.create(ADMIN_ID, ASTORIA_ID);
        int id = created.getId();
        Assertions.assertTrue(service.delete(id, ADMIN_ID));
        assertNull(service.get(ADMIN_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> service.delete(1, USER_ID));
    }

    @Test
    public void get() throws Exception {
        Vote vote = service.get(USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_4);
    }

    @Test
    public void getVotesCountForRestaurantToday() throws Exception {
        int count = service.getVotesCountForRestaurantToday(ASTORIA_ID);
        Assertions.assertEquals(1, count);
    }
}
