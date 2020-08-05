package graduation.graduationProject.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_idx")})
public class Restaurant extends AbstractNamedEntity {

    public Restaurant() {
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
