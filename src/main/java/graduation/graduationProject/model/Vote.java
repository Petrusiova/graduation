package graduation.graduationProject.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_idx")})
public class Vote extends AbstractBaseEntity{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    //    @NotNull(groups = View.Persist.class)
    private User user;

    @Column(name = "id_rest", nullable = false)
    @NotNull
    private int id_rest;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    // TODO: 06.04.2020 try to make it private
    public Vote() {
    }

    public Vote(@NotNull User user, @NotNull int id_rest, @NotNull LocalDate date) {
        this.user = user;
        this.id_rest = id_rest;
        this.date = date;
    }

    public Vote(int id, @NotNull User user, @NotNull int id_rest, @NotNull LocalDate date) {
        super(id);
        this.user = user;
        this.id_rest = id_rest;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Vote{" +
                " id: " + id +
                " date: " + date +
                " }";
    }
}
