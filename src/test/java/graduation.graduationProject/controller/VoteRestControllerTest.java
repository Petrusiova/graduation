package graduation.graduationProject.controller;

import graduation.graduationProject.UserTestData;
import graduation.graduationProject.VoteTestData;
import graduation.graduationProject.model.Vote;
import graduation.graduationProject.repository.VoteRepository;
import graduation.graduationProject.util.VoteUtil;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.json.JsonUtil;
import graduation.graduationProject.web.vote.VoteRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static graduation.graduationProject.RestaurantTestData.*;
import static graduation.graduationProject.TestUtil.readFromJson;
import static graduation.graduationProject.TestUtil.userHttpBasic;
import static graduation.graduationProject.UserTestData.USER;
import static graduation.graduationProject.VoteTestData.*;
import static graduation.graduationProject.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1));
    }
    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID + "000")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)));
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            action.andExpect(status().isNoContent());
            assertThrows(NotFoundException.class, () -> repository.get(VOTE_1_ID, UserTestData.USER_ID));
        } else {
            action.andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "000")
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();

        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1_ID + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(updated, "restaurant", ASTORIA))
                .with(userHttpBasic(USER)));

        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            action.andExpect(status().isNoContent());
            VOTE_MATCHER.assertMatch(repository.get(VOTE_1_ID, UserTestData.USER_ID), updated);
        } else {
            action.andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    void createWithLocation() throws Exception {
        Vote newVote = new Vote();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(newVote, "restaurant", ASTORIA))
                .with(userHttpBasic(USER)));

        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            Vote created = readFromJson(action, Vote.class);
            int newId = created.id();
            newVote.setId(newId);
            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(repository.get(newId, UserTestData.USER_ID), newVote);
        } else {
            action.andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.getTos(ALL_USER_RESTS)));
    }


    @Test
    void updateInvalid() throws Exception {
        Vote invalid = new Vote((LocalDate) null);
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1_ID + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void createDuplicate() throws Exception {
        Vote newVote = new Vote();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(newVote, "restaurant", ASTORIA))
                .with(userHttpBasic(USER)));

        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {

            Vote created = readFromJson(action, Vote.class);
            int newId = created.id();
            newVote.setId(newId);
            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(repository.get(newId, UserTestData.USER_ID), newVote);

            newVote = new Vote();
            action = perform(MockMvcRequestBuilders.post(REST_URL + "?id_rest=" + VICTORIA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeAdditionProps(newVote, "restaurant", ASTORIA))
                    .with(userHttpBasic(USER)));

            created = readFromJson(action, Vote.class);
            newId = created.id();
            newVote.setId(newId);
            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(repository.get(newId, UserTestData.USER_ID), newVote);
        } else {
            action.andExpect(status().isUnprocessableEntity());
        }
    }
}
