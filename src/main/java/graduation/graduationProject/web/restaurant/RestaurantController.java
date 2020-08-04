package graduation.graduationProject.web.restaurant;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.*;

@Controller
public class RestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

//    public List<Restaurant> getAll() {
//        log.info("getAll");
//        return repository.getAll();
//    }
//
//    public Restaurant get(int id) {
//        log.info("get {}", id);
//        Restaurant restaurant = repository.get(id);
//        if (restaurant != null ) {
//            return checkNotFoundWithId(restaurant, id);
//        }
//        else throw new NotFoundException("No restaurant with id " + id + " was found");
//    }
//
//    public Restaurant getByName(String name){
//        log.info("getByName {}", name);
//        Restaurant restaurant = repository.getByName(name);
//        String msg = "No restaurant with name " + name;
//        if (restaurant != null ) {
//            return checkNotFound(restaurant, msg);
//        }
//        else throw new NotFoundException(msg);
//    }
//
//    public Restaurant create(String name) {
//        log.info("create {}", name);
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName(name);
//        checkNew(restaurant);
//        return repository.save(restaurant);
//    }
//
//    public void delete(int id) {
//        log.info("delete {}", id);
//        checkNotFoundWithId(repository.delete(id), id);
//    }
//
//    public void update(Restaurant restaurant, int id) {
//        Assert.notNull(restaurant, "Restaurant cannot be null");
//        log.info("update {} with id={}", restaurant, id);
//        assureIdConsistent(restaurant, id);
//        repository.save(restaurant);
//    }
}
