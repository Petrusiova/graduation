package graduation.graduationProject.web.meal;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.repository.MealRepository;
import graduation.graduationProject.to.MealTo;
import graduation.graduationProject.util.MealsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public static final String REST_URL = "/rest/meals";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealRepository mealRepository;

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        log.info("get meal {}", id);
        return mealRepository.get(id);
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete meal {}", id);
        mealRepository.delete(id);
    }

    @GetMapping
    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(mealRepository.getAll());
    }

    @GetMapping("/byDate")
    public List<MealTo> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll by date {}", date);
        return MealsUtil.getTos(mealRepository.getAllByDate(date));
    }

    @GetMapping("/byRestaurant")
    public List<MealTo> getAllByRestaurant(@RequestParam int id_rest) {
        log.info("getAll by restaurant {}", id_rest);
        return MealsUtil.getTos(mealRepository.getAllByRestaurant(id_rest));
    }

    @GetMapping("/byRestaurantAndDate")
    public List<MealTo> getAllByRestaurantAndDate(@RequestParam int id_rest, @RequestParam LocalDate date) {
        log.info("getAll by restaurant {} and date {}", id_rest, date);
        return MealsUtil.getTos(mealRepository.getAllByRestaurantAndDate(id_rest, date));
    }

    @PutMapping(value = "/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Meal meal, @PathVariable int id, @RequestParam int id_rest) {
        assureIdConsistent(meal, id);
        log.info("update {} for restaurant {}", meal, id_rest);
        mealRepository.save(meal, id_rest);
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Validated @RequestBody Meal meal, @RequestParam int id_rest) {
        checkNew(meal);
        log.info("create {} for restaurant {}", meal, id_rest);
        Meal created = mealRepository.save(meal, id_rest);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}