package graduation.graduationProject.repository.jpa;

import graduation.graduationProject.model.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaMenuRepository {

    @PersistenceContext
    private EntityManager em;


    public Menu get(int id) {
        return em.find(Menu.class, id);
    }

    public Menu save(Menu menu) {
        if (menu.isNew()) {
            em.persist(menu);
            return menu;
        } else {
            return em.merge(menu);
        }
    }

    public boolean delete(int id) {
        return em.createNamedQuery(Menu.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public Menu getByRestaurant(int id_rest) {
        return (Menu) em.createNamedQuery(Menu.GET_BY_RESTAURANT)
                .setParameter("id_rest", id_rest)
                .getSingleResult();
    }

    public Menu getByRestaurantAndDate(int id_rest, LocalDate date) {
        return (Menu) em.createNamedQuery(Menu.GET_BY_RESTAURANT_AND_DATE)
                .setParameter("id_rest", id_rest)
                .setParameter("date", date)
                .getSingleResult();
    }

    public List<Menu> getAll() {
        return em.createNamedQuery(Menu.GET_ALL)
                .getResultList();
    }
}