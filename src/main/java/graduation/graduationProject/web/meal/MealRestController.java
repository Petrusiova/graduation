package graduation.graduationProject.web.meal;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.repository.MealRepository;
import graduation.graduationProject.to.MealTo;
import graduation.graduationProject.util.MealsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.assureIdConsistent;
import static graduation.graduationProject.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {
    public static final String REST_URL = "/rest";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealRepository mealRepository;

    @GetMapping("/restaurants/{restaurant_id}/meals/{meal_id}")
    public Meal get(@PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("get meal {}", meal_id);
        return mealRepository.get(meal_id);
    }

    @DeleteMapping("/admin/restaurants/{restaurant_id}/meals/{meal_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("delete meal {}", meal_id);
        mealRepository.delete(meal_id, restaurant_id);
    }

    @GetMapping("/meals/today")
    public List<MealTo> getAllToday() {
        log.info("getAll");
        return MealsUtil.getTos(mealRepository.getAllToday());
    }

    @GetMapping("/restaurants/{restaurant_id}/meals/today")
    public List<MealTo> getMealByRestaurantToday(@PathVariable int restaurant_id) {
        log.info("getAll by restaurant {} today", restaurant_id);
        return MealsUtil.getTos(mealRepository.getMealByRestaurantToday(restaurant_id, LocalDate.now()));
    }

    @PutMapping(value = "/admin/restaurants/{restaurant_id}/meals/{meal_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Meal meal, @PathVariable int restaurant_id, @PathVariable int meal_id) {
        assureIdConsistent(meal, meal_id);
        log.info("update {} for restaurant {}", meal, restaurant_id);
        mealRepository.save(meal, restaurant_id);
    }

    @PostMapping(value = "/admin/restaurants/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Validated @RequestBody Meal meal, @PathVariable int restaurant_id) {
        checkNew(meal);
        log.info("create {} for restaurant {}", meal, restaurant_id);
        Meal created = mealRepository.save(meal, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}