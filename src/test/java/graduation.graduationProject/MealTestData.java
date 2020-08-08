package graduation.graduationProject;

import graduation.graduationProject.model.Meal;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static TestMatcher<Meal> MEAL_MATCHER = TestMatcher.usingFieldsComparator(Meal.class, "restaurant", "id_rest");
//    public static TestMatcher<MenuTo> MENU_TO_MATCHER = TestMatcher.usingEquals(MenuTo.class);

    public static final Meal MEAL_1 = new Meal(START_SEQ + 5, LocalDate.of(2020, 4, 6), "soup", 50);
    public static final Meal MEAL_2 = new Meal(START_SEQ + 6, LocalDate.of(2020, 4, 6), "porridge", 25);
    public static final Meal MEAL_3 = new Meal(START_SEQ + 7, LocalDate.of(2020, 4, 6), "juice", 20);
    public static final Meal MEAL_4 = new Meal(START_SEQ + 8, LocalDate.of(2020, 4, 5), "coffee", 20);

    public static final List<Meal> MEALS = List.of(MEAL_1, MEAL_2, MEAL_3, MEAL_4);
    public static final List<Meal> ALL_MEALS_EQL_DATE = List.of(MEAL_1, MEAL_2, MEAL_3);


    public static final int MEAL_1_ID = START_SEQ + 5;
    public static final int MEAL_1_RESTAURANT_ID = START_SEQ + 2;



    public static Meal getNew() {
        return new Meal(LocalDate.now(), "potato", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1_ID, LocalDate.of(2020, 4, 6), "soup", 50);
        updated.setDescription("updatedMenu");
        return updated;
    }
}
