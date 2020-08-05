package graduation.graduationProject.repository;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

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

    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    public boolean delete(int id) {
        boolean found = crudRestaurantRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
        return true;
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
}