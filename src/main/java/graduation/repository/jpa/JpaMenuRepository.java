package graduation.repository.jpa;

import graduation.model.Menu;
import graduation.repository.MenuRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaMenuRepository implements MenuRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Menu get(int id) {
        return em.find(Menu.class, id);
    }


    // TODO: 06.04.2020 do save for Menu
    @Override
    @Transactional
    public Menu save(Menu menu) {
        if (menu.isNew()) {
            em.persist(menu);
            return menu;
        } else {
            return em.merge(menu);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Menu.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public List<Menu> getAll() {
        return em.createNamedQuery(Menu.ALL_SORTED, Menu.class).getResultList();
    }
}