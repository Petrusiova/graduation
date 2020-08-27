package graduation.graduationProject.web.restaurant;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.to.RestaurantTo;
import graduation.graduationProject.util.RestsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static graduation.graduationProject.util.ValidationUtil.assureIdConsistent;
import static graduation.graduationProject.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String REST_URL = "/rest/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UniqueNameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return restaurantRepository.get(id);
    }

    @GetMapping("/by")
    public Restaurant getByName(@RequestParam String name) {
        log.info("get restaurant {}", name);
        return restaurantRepository.getByName(name);
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.delete(id);
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestsUtil.getTos(restaurantRepository.getAll());
    }

    @GetMapping("/today")
    public List<RestaurantTo> getAllWithTodayMeals() {
        log.info("getAllWithTodayMeals");

        List<Restaurant> result = restaurantRepository.getAllWithTodayMeals()
                .stream()
                .filter(restaurant -> restaurant.getMeals()
                        .stream()
                        .anyMatch(meal -> meal.getDate().equals(LocalDate.now()))).collect(Collectors.toList());
        return RestsUtil.getTos(result);
    }

    @PutMapping(value = "/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant {} with id {}", restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant {}", restaurant);
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PatchMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        restaurantRepository.enable(id, enabled);
    }
}
