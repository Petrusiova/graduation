package testData;

import graduation.graduationProject.model.Menu;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuControllerTestData {

    public static final Menu MENU_1 = new Menu(100005, 100002, "soup", 50.5, LocalDate.of(2020, 4, 6));
    public static final Menu MENU_2 = new Menu(100006, 100003, "porridge", 25.5, LocalDate.of(2020, 4, 6));
    public static final Menu MENU_3 = new Menu(100007, 100003, "juice", 10.0, LocalDate.of(2020, 4, 6));
    public static final Menu MENU_4 = new Menu(100008, 100004, "coffee", 20.0, LocalDate.of(2020, 4, 6));

    public static List<Menu> getAllMenu(){
        return Stream.of(MENU_1, MENU_2, MENU_3, MENU_4)
                .sorted(Comparator.comparing(Menu::getDate).thenComparing(Menu::getId_rest).reversed())
                .collect(Collectors.toList());
    }

    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.of();

}
