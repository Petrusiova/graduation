package testData;

import graduation.graduationProject.model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestaurantControllerTestData {

    public static final Restaurant ASTORIA = new Restaurant(100002, "ASTORIA");
    public static final Restaurant VICTORIA = new Restaurant(100003, "VICTORIA");
    public static final Restaurant TIFFANY = new Restaurant(100004, "TIFFANY");


    public static List<Restaurant> getAllRestaurants(){
        return Stream.of(ASTORIA, VICTORIA, TIFFANY)
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }

    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.of();

}
