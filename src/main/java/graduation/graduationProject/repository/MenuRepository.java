package graduation.graduationProject.repository;

import graduation.graduationProject.model.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Repository
public class MenuRepository {

    private final CrudMenuRepository crudMenuRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public MenuRepository(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Menu save(Menu menu, int id_rest) {
        if (!menu.isNew() && get(menu.getId(), id_rest) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.get(id_rest));
        return crudMenuRepository.save(menu);
    }

    public boolean delete(int id, int userId) {
        return crudMenuRepository.delete(id, userId) != 0;
    }

    public Menu get(int id, int id_rest) {
        return crudMenuRepository.findById(id).filter(item -> id_rest == item.getRestaurant().getId()).orElse(null);
    }

    public List<Menu> getAll() {
        return crudMenuRepository.findAll();
    }

    public List<Menu> getAllByDate(LocalDate date) {
        return crudMenuRepository.getAllByDate(date);
    }

    public List<Menu> getAllByRestaurant(int id_rest) {
        return crudMenuRepository.getAllByRestaurant(id_rest);
    }

    public List<Menu> getAllByRestaurantAndDate(int id_rest, LocalDate date) {
        return crudMenuRepository.getByRestaurantAndDate(id_rest, date);
    }
}