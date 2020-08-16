package graduation.graduationProject.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class MealTo extends BaseTo {

    private final LocalDate date;
    private final String description;
    private final Integer price;
//    private final String restaurantName; // TODO: 15.08.2020 necessary???

    @ConstructorProperties({"id", "date", "description", "price"})
    public MealTo(Integer id, LocalDate date, String description, Integer price) {
        super(id);
        this.date = date;
        this.description = description;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealTo mealTo = (MealTo) o;
        return Objects.equals(date, mealTo.date) &&
                Objects.equals(description, mealTo.description) &&
                Objects.equals(price, mealTo.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, description, price);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
