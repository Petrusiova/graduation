package graduation.model;

import java.util.Map;

public class Restaurant extends AbstractNamedEntity {

    private String name;
    private Map<String, Integer> menu;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

//    public Restaurant(Integer id, String name, Map<String, Integer> menu) {
//        super(id, name);
//        setMenu(menu);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Integer> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name=" + name +
                ", menu=" + menu +
                '}';
    }
}
