package graduation.graduationProject.web.meal;

import graduation.graduationProject.View;
import graduation.graduationProject.model.Meal;
import graduation.graduationProject.repository.MealRepository;
import graduation.graduationProject.to.MealTo;
import graduation.graduationProject.util.MealsUtil;
import graduation.graduationProject.web.AbstractController;
import graduation.graduationProject.web.SecurityUtil;
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
public class MealRestController extends AbstractController {
    static final String REST_URL = "/rest/profile/meals";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealRepository mealRepository;

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        log.info("get meal {}", id);
        return mealRepository.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete meal {}", id);
        checkModificationAllowed(SecurityUtil.authUserId());
        mealRepository.delete(id);
    }

    @GetMapping
    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(mealRepository.getAll());
    }

    @GetMapping("/by")
    public List<MealTo> getAllByDate(@RequestParam LocalDate date) {
        log.info("getAll by date {}", date);
        return MealsUtil.getTos(mealRepository.getAllByDate(date));
    }

    @GetMapping("/by")
    public List<MealTo> getAllByRestaurant(@RequestParam int id_rest) {
        log.info("getAll by restaurant {}", id_rest);
        return MealsUtil.getTos(mealRepository.getAllByRestaurant(id_rest));
    }

    @GetMapping("/by")
    public List<MealTo> getAllByRestaurantAndDate(@RequestParam int id_rest, @RequestParam LocalDate date) {
        log.info("getAll by restaurant {} and date {}", id_rest, date);
        return MealsUtil.getTos(mealRepository.getAllByRestaurantAndDate(id_rest, date));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Meal meal, @PathVariable int id, @RequestParam int id_rest) {
        assureIdConsistent(meal, id);
        log.info("update {} for restaurant {}", meal, id_rest);
        checkModificationAllowed(SecurityUtil.authUserId());
        mealRepository.save(meal, id_rest);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Validated(View.Web.class) @RequestBody Meal meal, @RequestParam int id_rest) {
        checkNew(meal);
        log.info("create {} for restaurant {}", meal, id_rest);
        checkModificationAllowed(SecurityUtil.authUserId());
        Meal created = mealRepository.save(meal, id_rest);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}