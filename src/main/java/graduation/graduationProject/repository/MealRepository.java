package graduation.graduationProject.repository;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;


@Repository
public class MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public MealRepository(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Meal save(Meal Meal, int restaurant_id) {
        Meal.setRestaurant(crudRestaurantRepository.getOne(restaurant_id));
        return crudMealRepository.save(Meal);
    }

    public boolean delete(int id, int restaurant_id) {
        checkNotFoundWithId(crudMealRepository.delete(id, restaurant_id) != 0, id);
        return true;
    }

    public Meal get(int id) {
        return crudMealRepository.findById(id).orElseThrow(() -> new NotFoundException("No meal with id = " + id));
    }

    public List<Meal> getAll() {
        return crudMealRepository.getAll();
    }

    public List<Meal> getAllByDate(LocalDate date) {
        return crudMealRepository.getAllByDate(date);
    }

    public List<Meal> getAllByRestaurant(int restaurant_id) {
        return crudMealRepository.getAllByRestaurant(restaurant_id);
    }

    public List<Meal> getMealByRestaurantToday(int restaurant_id, LocalDate date) {
        return crudMealRepository.getMealByRestaurantToday(restaurant_id, date);
    }

    public List<Meal> getAllToday() {
        return crudMealRepository.getAllToday(LocalDate.now());
    }
}