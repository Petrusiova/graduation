package graduation.graduationProject.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Menu.DELETE, query = "DELETE FROM Menu m WHERE m.id=:id"),
        @NamedQuery(name = Menu.GET_BY_RESTAURANT, query = "SELECT m FROM Menu m WHERE m.id_rest=:id_rest ORDER BY m.date DESC"),
        @NamedQuery(name = Menu.GET_BY_RESTAURANT_AND_DATE, query = "SELECT m FROM Menu m WHERE m.id_rest=:id_rest and m.date=:date ORDER BY m.date DESC"),
        @NamedQuery(name = Menu.GET_BY_DATE, query = "SELECT m FROM Menu m WHERE m.date=:date ORDER BY m.date, m.id_rest DESC"),
        @NamedQuery(name = Menu.GET_ALL, query = "SELECT m FROM Menu m ORDER BY m.date, m.id_rest DESC")
})
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"meal", "date"}, name = "menu_unique_restaurant_meal_date_idx")})
public class Menu extends AbstractBaseEntity {

    public static final String DELETE = "Menu.delete";
    public static final String GET_ALL = "Menu.getAll";
    public static final String GET_BY_RESTAURANT = "Menu.getByRestaurant";
    public static final String GET_BY_RESTAURANT_AND_DATE = "Menu.getByRestaurantAndDate";
    public static final String GET_BY_DATE = "Menu.getByDate";

    @Column(name = "id_rest", nullable = false)
    @NotNull
    private Integer id_rest;

    @Column(name = "meal", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String meal;

    @Column(name = "price", nullable = false, columnDefinition = "double")
    @NotNull
    private Double price;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    public Menu(){

    }

    public Menu(int id_rest, String meal, Double price){
        this(null, id_rest, meal, price, LocalDate.now());
    }

    public Menu(int id_rest, String meal, Double price, LocalDate date){
        this(null, id_rest, meal, price, date);
    }

    public Menu(Integer id, int id_rest, String meal, Double price){
        this(id, id_rest, meal, price, LocalDate.now());

    }

    public Menu(Integer id, int id_rest, String meal, Double price, LocalDate date){
        super(id);
        this.id_rest = id_rest;
        this.meal = meal;
        this.price = price;
        this.date = date;

    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getId_rest() {
        return id_rest;
    }

    public void setId_rest(Integer id_rest) {
        this.id_rest = id_rest;
    }
}
