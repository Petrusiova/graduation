package graduation.graduationProject;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.to.MealTo;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static TestMatcher<Meal> MEAL_MATCHER = TestMatcher.usingFieldsComparator(Meal.class, "restaurant", "restaurant_id");
    public static TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingEquals(MealTo.class);

    public static final Meal MEAL_1 = new Meal(START_SEQ + 5, LocalDate.of(2020, 4, 6), "soup", 50);
    public static final Meal MEAL_2 = new Meal(START_SEQ + 6, LocalDate.of(2020, 4, 6), "porridge", 25);
    public static final Meal MEAL_3 = new Meal(START_SEQ + 7, LocalDate.of(2020, 4, 6), "juice", 10);
    public static final Meal MEAL_4 = new Meal(START_SEQ + 8, LocalDate.now(), "coffee", 20);

    public static final int MEAL_1_ID = START_SEQ + 5;

    public static Meal getNew() {
        return new Meal(null, LocalDate.now(), "potato", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1_ID, LocalDate.of(2020, 4, 6), "soup", 50);
        updated.setDescription("updatedMenu");
        return updated;
    }
}
