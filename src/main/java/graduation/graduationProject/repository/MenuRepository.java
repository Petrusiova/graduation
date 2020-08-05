package graduation.graduationProject.repository;

import graduation.graduationProject.model.Menu;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;


@Repository
public class MenuRepository {

    private static final Sort SORT_DATE_DESC = Sort.by(Sort.Direction.DESC, "date");

    private final CrudMenuRepository crudMenuRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public MenuRepository(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Menu save(Menu menu, int id_rest) {
        menu.setRestaurant(crudRestaurantRepository.get(id_rest));
        return crudMenuRepository.save(menu);
    }

    public boolean delete(int id) {
        checkNotFoundWithId(crudMenuRepository.delete(id) != 0, id);
        return true;
    }

    public Menu get(int id) {
        return crudMenuRepository.findById(id).orElseThrow(() -> new NotFoundException("No menu with id = " + id));
    }

    public List<Menu> getAll() {
        return crudMenuRepository.findAll(SORT_DATE_DESC);
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