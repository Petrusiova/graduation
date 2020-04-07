package graduation.graduationProject.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Restaurant.GET, query = "SELECT r FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.VALIDATE_USER, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id=:id ORDER BY u.name, u.email"),
        @NamedQuery(name = Restaurant.GET_ALL, query = "SELECT r FROM Restaurant r ORDER BY r.name DESC")
})
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_idx")})
public class Restaurant extends AbstractNamedEntity {

    public static final String GET = "Restaurant.get";
    public static final String DELETE = "Restaurant.delete";
    public static final String GET_ALL = "Restaurant.getAll";
    public static final String VALIDATE_USER = "User.validateUser";


    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

}
