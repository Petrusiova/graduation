package graduation.graduationProject;

import graduation.graduationProject.model.Menu;
import graduation.graduationProject.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingFieldsComparator(Menu.class, "restaurant");
//    public static TestMatcher<MenuTo> MENU_TO_MATCHER = TestMatcher.usingEquals(MenuTo.class);

    public static final Menu MENU_1 = new Menu(START_SEQ + 5, "soup", 50, LocalDate.of(2020, 4, 6));
    public static final Menu MENU_2 = new Menu(START_SEQ + 6, "porridge", 25, LocalDate.of(2020, 4, 6));
    public static final Menu MENU_3 = new Menu(START_SEQ + 7, "juice", 20, LocalDate.of(2020, 4, 6));
    public static final Menu MENU_4 = new Menu(START_SEQ + 8, "coffee", 20, LocalDate.of(2020, 4, 5));

    public static final List<Menu> ALL_MENU = List.of(MENU_1, MENU_2, MENU_3, MENU_4);
    public static final List<Menu> ALL_MENU_EQL_DATE = List.of(MENU_1, MENU_2, MENU_3);
    public static final LocalDate MENU_DATE = LocalDate.of(2020, 4, 6);

    public static final int MENU_1_ID = START_SEQ + 5;
    public static final int MENU_1_RESTAURANT_ID = 100002;



    public static Menu getNew() {
        return new Menu("potato", 500);
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(MENU_1);
        updated.setMeal("updatedMenu");
        return updated;
    }
}
