package graduation.graduationProject.repository;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;


@Repository
public class MealRepository {

//    private static final Sort SORT_DATE_DESC = Sort.by(Sort.Direction.DESC, "date");

    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public MealRepository(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Meal save(Meal Meal, int id_rest) {
        Meal.setRestaurant(crudRestaurantRepository.getOne(id_rest));
        return crudMealRepository.save(Meal);
    }

    public boolean delete(int id) {
        checkNotFoundWithId(crudMealRepository.delete(id) != 0, id);
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

    public List<Meal> getAllByRestaurant(int id_rest) {
        return crudMealRepository.getAllByRestaurant(id_rest);
    }

    public List<Meal> getAllByRestaurantAndDate(int id_rest, LocalDate date) {
        return crudMealRepository.getByRestaurantAndDate(id_rest, date);
    }

    public Meal getWithRestaurant(int id, int id_rest) {
        return checkNotFoundWithId(crudMealRepository.getWithRestaurant(id, id_rest), id);
    }
}