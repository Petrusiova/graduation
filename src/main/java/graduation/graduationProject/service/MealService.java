package graduation.graduationProject.service;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.repository.CrudMealRepository;
import graduation.graduationProject.repository.CrudRestaurantRepository;
import graduation.graduationProject.util.exception.ModificationRestrictionException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;


@Repository
public class MealService {

    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public MealService(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public void update(Meal meal, int restaurant_id) {
        Assert.notNull(meal, "meal must not be null");
        if (!meal.isNew() && get(restaurant_id, meal.id()) == null) {
            throw new ModificationRestrictionException();
        }
        meal.setRestaurant(crudRestaurantRepository.getOne(restaurant_id));
        checkNotFoundWithId(crudMealRepository.save(meal), meal.id());
    }

    public Meal create(Meal meal, int restaurant_id) {
        Assert.notNull(meal, "meal must not be null");
        meal.setRestaurant(crudRestaurantRepository.getOne(restaurant_id));
        return crudMealRepository.save(meal);
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