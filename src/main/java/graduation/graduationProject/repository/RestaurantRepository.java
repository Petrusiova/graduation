package graduation.graduationProject.repository;

import graduation.graduationProject.model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
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
        return crudRestaurantRepository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    public Restaurant getByName(String name) {
        return crudRestaurantRepository.getByName(name);
    }

    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }
}