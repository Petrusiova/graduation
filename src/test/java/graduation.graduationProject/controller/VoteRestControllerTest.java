package graduation.graduationProject.controller;

import graduation.graduationProject.UserTestData;
import graduation.graduationProject.model.Vote;
import graduation.graduationProject.service.VoteRepository;
import graduation.graduationProject.util.VoteUtil;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.vote.VoteRestController;
import org.junit.jupiter.api.Assertions;
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
import static graduation.graduationProject.UserTestData.ADMIN;
import static graduation.graduationProject.UserTestData.USER;
import static graduation.graduationProject.VoteTestData.*;
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
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + "mineToday")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk());
        Assertions.assertEquals(VOTE_4, readFromJson(action, Vote.class));
    }

    @Test
    void getVotesCountForRestaurantToday() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + "todayByRestaurant/?restaurant_id=" + ASTORIA_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Assertions.assertEquals(1, readFromJson(action, Integer.class));
    }


    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)));
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            action.andExpect(status().isNoContent());
            assertThrows(NotFoundException.class, () -> repository.get(UserTestData.USER_ID));
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
    void createWithLocation() throws Exception {
        Vote newVote = new Vote();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurant_id=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)));

            Vote created = readFromJson(action, Vote.class);
            int newId = created.id();
            newVote.setId(newId);
            VOTE_MATCHER.assertMatch(created, newVote);
            Assertions.assertEquals(repository.get(UserTestData.USER_ID).getRestaurant(), ASTORIA);
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
    void createDuplicate() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurant_id=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER)));

        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurant_id=" + VICTORIA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(userHttpBasic(USER)));

        Assertions.assertEquals(1, repository.getAll(USER.getId()).stream()
                        .filter(vote -> vote.getDate().equals(LocalDate.now())).count());
    }
}
