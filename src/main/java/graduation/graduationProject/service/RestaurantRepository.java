package graduation.graduationProject.service;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.CrudRestaurantRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        String name = restaurant.getName().toUpperCase();
        restaurant.setName(name);
        return crudRestaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("No restaurant with restaurantId = " + id));
    }

    public Restaurant getByName(String name) {
        return checkNotFound(crudRestaurantRepository.getByName(name), "name = " + name);
    }

    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllWithTodayMeals(){
        return crudRestaurantRepository.getAllWithTodayMeals(LocalDate.now());
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