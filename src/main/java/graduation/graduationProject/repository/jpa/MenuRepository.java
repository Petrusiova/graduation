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
public class MenuRepository {

    @PersistenceContext
    private EntityManager em;


    public Menu get(int id) {
        return em.find(Menu.class, id);
    }

    @Transactional
    public Menu save(Menu menu) {
        if (menu.isNew()) {
            em.persist(menu);
            return menu;
        } else {
            return em.merge(menu);
        }
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Menu.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public List<Menu> getByRestaurant(int id_rest) {
        return em.createNamedQuery(Menu.GET_BY_RESTAURANT)
                .setParameter("id_rest", id_rest)
                .getResultList();
    }

    public List<Menu> getByRestaurantAndDate(int id_rest, LocalDate date) {
        return em.createNamedQuery(Menu.GET_BY_RESTAURANT_AND_DATE)
                .setParameter("id_rest", id_rest)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Menu> getByDate(LocalDate date) {
        return em.createNamedQuery(Menu.GET_BY_DATE)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Menu> getAll() {
        return em.createNamedQuery(Menu.GET_ALL)
                .getResultList();
    }
}