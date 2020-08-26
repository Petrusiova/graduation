package graduation.graduationProject.controller;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.repository.MealRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.json.JsonUtil;
import graduation.graduationProject.web.meal.MealRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static graduation.graduationProject.MealTestData.*;
import static graduation.graduationProject.RestaurantTestData.ASTORIA;
import static graduation.graduationProject.RestaurantTestData.ASTORIA_ID;
import static graduation.graduationProject.TestUtil.readFromJson;
import static graduation.graduationProject.TestUtil.userHttpBasic;
import static graduation.graduationProject.UserTestData.ADMIN;
import static graduation.graduationProject.UserTestData.USER;
import static graduation.graduationProject.model.AbstractBaseEntity.START_SEQ;
import static graduation.graduationProject.util.MealsUtil.getTos;
import static graduation.graduationProject.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL_1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "000")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "admin/" + MEAL_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.get(MEAL_1_ID));
    }

    @Test
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "admin/" + MEAL_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "admin/" + "000")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "admin/" + MEAL_1_ID + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(updated, "restaurant", ASTORIA))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(repository.get(MEAL_1_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "admin/?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(newMeal, "restaurant", ASTORIA))
                .with(userHttpBasic(ADMIN)));

        Meal created = readFromJson(action, Meal.class);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.get(newId), newMeal);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(MEALS)));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL  + "byDate/?date=2020-04-06")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(ALL_MEALS_EQL_DATE)));
    }

    @Test
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL  + "byRestaurant/?id_rest=" + (START_SEQ + 3))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(Arrays.asList(MEAL_2, MEAL_3))));
    }

    @Test
    void getAllByRestaurantAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL  + "byRestaurantAndDate/?id_rest=" + (START_SEQ + 3) + "&date=2020-04-06")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(Arrays.asList(MEAL_2, MEAL_3))));
    }

    @Test
    void createInvalid() throws Exception {
        Meal invalid = new Meal(null, null, "Dummy", 200);
        perform(MockMvcRequestBuilders.post(REST_URL + "admin/" + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Meal invalid = new Meal(MEAL_1_ID, null, null, 6000);
        perform(MockMvcRequestBuilders.put(REST_URL + "admin/" + MEAL_1_ID + "?id_rest=" + ASTORIA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}
