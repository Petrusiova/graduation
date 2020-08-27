package graduation.graduationProject.web.restaurant;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.repository.RestaurantRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.ExceptionInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class UniqueNameValidator implements org.springframework.validation.Validator {

    @Autowired
    private RestaurantRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = ((Restaurant) target);
        Restaurant dbRestaurant = null;
        try {
            dbRestaurant = repository.getByName(restaurant.getName().toUpperCase());
        } catch (NotFoundException e) { }

        if (dbRestaurant != null && !dbRestaurant.getId().equals(restaurant.getId())) {
            errors.rejectValue("name", ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME);
        }
    }
}
