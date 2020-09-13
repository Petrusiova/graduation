package graduation.graduationProject.repository;

import graduation.graduationProject.model.Meal;
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

    public Meal get(int restaurant_id, int meal_Id) {
        return checkNotFoundWithId(crudMealRepository.get(restaurant_id, meal_Id), meal_Id);
    }

    public List<Meal> getMealByRestaurantToday(int restaurant_id) {
        return crudMealRepository.getMealByRestaurantToday(restaurant_id, LocalDate.now());
    }
}