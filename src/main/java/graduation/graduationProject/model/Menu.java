package graduation.graduationProject.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"meal", "date", "id_rest"}, name = "menu_unique_restaurant_meal_date_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "meal", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String meal;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rest", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    @NotNull(groups = View.Persist.class)
    @NotNull
    private Restaurant restaurant;

    public Menu(){
    }
    
    public Menu(@NotNull Menu m){
        this(m.getId(), m.getMeal(), m.getPrice(), m.getDate());
    }

    public Menu(String meal, Integer price){
        this(null, meal, price, LocalDate.now());
    }

    public Menu(String meal, Integer price, LocalDate date){
        this(null, meal, price, date);
    }

    public Menu(Integer id, String meal, Integer price){
        this(id, meal, price, LocalDate.now());
    }

    public Menu(Integer id, String meal, Integer price, LocalDate date){
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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
