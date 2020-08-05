package graduation.graduationProject;

import graduation.graduationProject.model.Restaurant;

import java.util.List;

import static graduation.graduationProject.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static TestMatcher<Restaurant> REST_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "menus");

    public static final Restaurant ASTORIA = new Restaurant(START_SEQ + 2, "ASTORIA");
    public static final Restaurant VICTORIA = new Restaurant(START_SEQ + 3, "VICTORIA");
    public static final Restaurant TIFFANY = new Restaurant(START_SEQ + 4, "TIFFANY");

    public static final List<Restaurant> ALL_RESTS = List.of(ASTORIA, VICTORIA, TIFFANY);

    public static final int ASTORIA_ID = START_SEQ + 2;

    public static Restaurant getNew() {
        return new Restaurant("FANAGORIA");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(ASTORIA);
        updated.setName("updatedRestrnt");
        return updated;
    }
}
