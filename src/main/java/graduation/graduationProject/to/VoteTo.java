package graduation.graduationProject.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo extends BaseTo{

    private String restaurant;
    private LocalDate date;

    @ConstructorProperties({"id", "restaurant", "date"})
    public VoteTo(Integer id, String restaurant, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(restaurant, voteTo.restaurant) &&
                Objects.equals(date, voteTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant, date);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurant='" + restaurant + '\'' +
                ", date=" + date +
                '}';
    }
}
