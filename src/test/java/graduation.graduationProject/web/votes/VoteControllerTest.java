package graduation.graduationProject.web.votes;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.util.exception.IllegalTimeException;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.vote.VoteController;
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

import java.time.LocalTime;
import java.util.Collections;

import static testData.voteControllerTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteControllerTest {

    @Autowired
    private VoteController controller;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void create() {
        Vote vote = controller.create(100004);
        int id = vote.getId();
        VOTE_MATCHER.assertMatch(vote, controller.get(id));
    }

    @Test
    public void createIllegal(){
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage("could not execute statement");
        controller.create(-150);
    }

    @Test
    public void updateRightTime() {
        prepareForUpdate(LocalTime.of(11,0,0));
    }

    @Test
    public void updateWrongTime() {
        thrown.expect(IllegalTimeException.class);
        prepareForUpdate(LocalTime.of(11,0,1));
    }

    @Test
    public void updateNotOwn(){
        thrown.expect(NotFoundException.class);
        Vote vote = controller.create(100004);
        int id = vote.getId();
        vote.setUser_id(100001);
        controller.update(vote, id, LocalTime.of(10, 59));
    }

    @Test
    public void getAll(){
        VOTE_MATCHER.assertMatch(Collections.singletonList(VOTE_1), controller.getAll());
    }

    @Test
    public void get(){
        VOTE_MATCHER.assertMatch(VOTE_1, controller.get(VOTE_1.getId()));
    }

    @Test
    public void getNotFound(){
        thrown.expect(NotFoundException.class);
        controller.get(-150);
    }

    @Test
    public void getNotOwn(){
        thrown.expect(NotFoundException.class);
        controller.get(VOTE_2.getId());
    }

    private void prepareForUpdate(LocalTime time) {
        Vote vote = controller.create(100004);
        int id = vote.getId();
        vote.setId_rest(100003);
        controller.update(vote, id, time);
        Assert.assertEquals(100003, controller.get(id).getId_rest());
    }
}