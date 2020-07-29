package graduation.graduationProject.to;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

public class MenuTo extends BaseTo {

    private final String restaurantName;
    private final String meal;
    private final Double price;
    private final LocalDateTime dateTime;

    @ConstructorProperties({"id", "restaurantName", "meal", "price", "dateTime"})
    public MenuTo(Integer id, String restaurantName, String meal, Double price, LocalDateTime dateTime) {
        super(id);
        this.restaurantName = restaurantName;
        this.meal = meal;
        this.price = price;
        this.dateTime = dateTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getMeal() {
        return meal;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTo menuTo = (MenuTo) o;
        return Objects.equals(restaurantName, menuTo.restaurantName) &&
                Objects.equals(meal, menuTo.meal) &&
                Objects.equals(price, menuTo.price) &&
                Objects.equals(dateTime, menuTo.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName, meal, price, dateTime);
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", meal='" + meal + '\'' +
                ", price=" + price +
                ", dateTime=" + dateTime +
                '}';
    }
}
