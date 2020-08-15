package graduation.graduationProject.util;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.to.MealTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MealsUtil {

    private MealsUtil() {
    }

    public static List<MealTo> getTos(Collection<Meal> meals) {
        return meals.stream().map(MealsUtil::createTo).collect(Collectors.toList());
    }


    public static MealTo createTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getDate(), meal.getDescription(), meal.getPrice());
    }
}
