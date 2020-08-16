package graduation.graduationProject.web.restaurant;

import graduation.graduationProject.View;
import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.to.RestaurantTo;
import graduation.graduationProject.util.RestsUtil;
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
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.assureIdConsistent;
import static graduation.graduationProject.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractController {
    static final String REST_URL = "/rest/profile/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return restaurantRepository.get(id);
    }

    @GetMapping("/{name}")
    public Restaurant getByName(@PathVariable String name) {
        log.info("get restaurant {}", name);
        return restaurantRepository.getByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        checkModificationAllowed(SecurityUtil.authUserId());
        restaurantRepository.delete(id);
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestsUtil.getTos(restaurantRepository.getAll());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant {} with id {}", restaurant, id);
        checkModificationAllowed(SecurityUtil.authUserId());
        restaurantRepository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant {}", restaurant);
        checkModificationAllowed(SecurityUtil.authUserId());
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        checkModificationAllowed(SecurityUtil.authUserId());
        restaurantRepository.enable(id, enabled);
    }
}
