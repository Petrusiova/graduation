package graduation.graduationProject.util;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.to.MealTo;
import graduation.graduationProject.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RestsUtil {

    private RestsUtil() {
    }

    public static List<RestaurantTo> getTos(Collection<Restaurant> rests) {
        return rests.stream().map(RestsUtil::createTo).collect(Collectors.toList());
    }


    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}
