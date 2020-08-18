package graduation.graduationProject.controller;

import graduation.graduationProject.UserTestData;
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
import org.springframework.web.util.NestedServletException;

import java.time.LocalTime;

import static graduation.graduationProject.RestaurantTestData.ASTORIA;
import static graduation.graduationProject.RestaurantTestData.ASTORIA_ID;
import static graduation.graduationProject.TestUtil.readFromJson;
import static graduation.graduationProject.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: 16.08.2020 turn on checkModificationAllowed() in MealRestController after adding ADMIN in tests
public class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1));
    }

    @Test
    void delete() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)).andExpect(status().isNoContent());
            assertThrows(NotFoundException.class, () -> repository.get(VOTE_1_ID, UserTestData.USER_ID));
        } else {
            assertThrows(NestedServletException.class, () ->
                    perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)));
        }
    }

    @Test
    void update() throws Exception {
        Vote updated = getUpdated();
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1_ID + "?id_rest=" + ASTORIA_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeAdditionProps(updated, "restaurant", ASTORIA)))
                    .andExpect(status().isNoContent());
            VOTE_MATCHER.assertMatch(repository.get(VOTE_1_ID, UserTestData.USER_ID), updated);
        } else {
            assertThrows(NestedServletException.class, () ->
                    perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1_ID + "?id_rest=" + ASTORIA_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.writeAdditionProps(updated, "restaurant", ASTORIA))));
        }
    }

    @Test
    void createWithLocation() throws Exception {
        Vote newVote = getNew();
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?id_rest=" + ASTORIA_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeAdditionProps(newVote, "restaurant", ASTORIA)));
            Vote created = readFromJson(action, Vote.class);
            int newId = created.id();
            newVote.setId(newId);
            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(repository.get(newId, UserTestData.USER_ID), newVote);
        } else {
            assertThrows(NestedServletException.class, () ->
                    perform(MockMvcRequestBuilders.post(REST_URL + "?id_rest=" + ASTORIA_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeAdditionProps(newVote, "restaurant", ASTORIA))));
        }
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.getTos(ALL_RESTS)));
    }
}
