package graduation.graduationProject.repository;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.model.User;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFound;
import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;


@Repository
public class RestaurantRepository {

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("No restaurant with id = " + id));
    }

    public Restaurant getByName(String name) {
        return checkNotFound(crudRestaurantRepository.getByName(name), "name = " + name);
    }

    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }

    public Restaurant getWithMeals(int id){
        return checkNotFoundWithId(crudRestaurantRepository.getWithMeals(id), id);
    }

    public Restaurant getWithVotes(int id){
        return checkNotFoundWithId(crudRestaurantRepository.getWithVotes(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        Restaurant restaurant = checkNotFoundWithId(get(id), id);
        restaurant.setEnabled(enabled);
        crudRestaurantRepository.save(restaurant);
    }

    /**
     * could be used to delete only a new user with no relations
     * Use {@link #enable(int, boolean)} to turn off the opportunity to add meals and votes
     **/
    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id) {
        boolean found = crudRestaurantRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
        return true;
    }
}