package graduation.graduationProject.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
//        @NamedQuery(name = Menu.GET_BY_RESTAURANT, query = "SELECT m FROM Menu m WHERE m.restaurant.id=:id_rest ORDER BY m.date DESC"),
//        @NamedQuery(name = Menu.GET_BY_RESTAURANT_AND_DATE, query = "SELECT m FROM Menu m WHERE m.restaurant.id=:id_rest and m.date=:date ORDER BY m.date DESC"),
        @NamedQuery(name = Vote.GET_ALL, query = "SELECT v FROM Vote v where v.user_id=:user_id order by v.date desc")
})
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_idx")})
public class Vote extends AbstractBaseEntity{

    public static final String GET_ALL = "Vote.getAll";
//    public static final String GET_BY_RESTAURANT = "Vote.getByRestaurant";
//    public static final String GET_BY_RESTAURANT_AND_DATE = "Vote.getByRestaurantAndDate";

    @Column(name = "user_id", nullable = false, columnDefinition = "int")
    @NotNull
    private int user_id;

    @Column(name = "id_rest", nullable = false, columnDefinition = "int")
    @NotNull
    private int id_rest;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    // TODO: 06.04.2020 try to make it private
    public Vote() {
    }

    public Vote(@NotNull int user_id, @NotNull int id_rest, @NotNull LocalDate date) {
        this.user_id = user_id;
        this.id_rest = id_rest;
        this.date = date;
    }

    public Vote(int id, @NotNull int user_id, @NotNull int id_rest, @NotNull LocalDate date) {
        super(id);
        this.user_id = user_id;
        this.id_rest = id_rest;
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId_rest() {
        return id_rest;
    }

    public void setId_rest(int id_rest) {
        this.id_rest = id_rest;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
