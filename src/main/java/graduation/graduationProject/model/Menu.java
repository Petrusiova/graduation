package graduation.graduationProject.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"meal", "date"}, name = "menu_unique_restaurant_meal_date_idx")})
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rest", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", meal='" + meal +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
