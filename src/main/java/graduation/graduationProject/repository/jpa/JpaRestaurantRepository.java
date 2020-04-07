package graduation.graduationProject.repository.jpa;

import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.util.RestaurantUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository {

    @PersistenceContext
    private EntityManager em;


    public Restaurant save(Restaurant restaurant, int user_id) {
        RestaurantUtil.validateAdmin(em, user_id);
            if (restaurant.isNew()) {
                em.persist(restaurant);
                return restaurant;
            } else {
                return em.merge(restaurant);
            }
    }

    public boolean delete(int id, int user_id) {
        RestaurantUtil.validateAdmin(em, user_id);
        return em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.GET_ALL)
                .getResultList();
    }
}