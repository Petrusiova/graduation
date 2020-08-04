package graduation.graduationProject.web.menu;

import graduation.graduationProject.model.Menu;
import graduation.graduationProject.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MenuController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuRepository repository;

//    public MenuController(MenuRepository repository) {
//        this.repository = repository;
//    }

    public List<Menu> getAll() {
        log.info("getAll");
        return repository.getAll();
    }
//
//    public Menu get(int id) {
//        log.info("get {}", id);
//        int userId = SecurityUtil.authUserId();
//        Menu menu = repository.get(id, userId);
//        if (menu != null ) {
//            return checkNotFoundWithId(repository.get(id, userId), id);
//        }
//        else throw new NotFoundException("No menu with id " + id + " was found");
//    }
//
//    public List<Menu> getByRestaurant(int id_rest) {
//        log.info("getByRestaurant {}", id_rest);
//        List<Menu> menu = repository.getAllByRestaurant(id_rest);
//        if (menu != null ) {
//            return menu;
//        }
//        else throw new NotFoundException("No menu with restaurant's id " + id_rest + " was found");
//    }
//
//    public List<Menu> getByDate(LocalDate date) {
//        Assert.notNull(date, "Date cannot be null");
//        log.info("getByDate {}", date);
//        List<Menu> menu = repository.getAllByDate(date);
//        if (menu != null ) {
//            return menu;
//        }
//        else throw new NotFoundException("No menu with date " + date + " was found");
//    }
//
//    public List<Menu> getByRestaurantAndDate(int id_rest, LocalDate date) {
//        log.info("getByRestaurantAndDate {} {}", id_rest, date);
//        List<Menu> menu = repository.getAllByRestaurantAndDate(id_rest, date);
//        if (menu != null ) {
//            return menu;
//        }
//        else throw new NotFoundException("No menu with restaurant's id " + id_rest + " and date" + date + " was found");
//    }
//
//    public Menu create(Menu menu) {
//        log.info("create {}", menu);
//        checkNew(menu);
//        return repository.save(menu, 0); // TODO: 01.08.2020 fix it
//    }
//
//    public void delete(int id) {
//        log.info("delete {}", id);
//        int userId = SecurityUtil.authUserId();
//        checkNotFoundWithId(repository.delete(id, userId), id);
//    }
//
//    public void update(Menu menu, int id) {
//        Assert.notNull(menu, "Menu cannot be null");
//        log.info("update {} with id={}", menu, id);
//        assureIdConsistent(menu, id);
//        int userId = SecurityUtil.authUserId();
//        repository.save(menu, userId); // TODO: 01.08.2020 check it
//    }
}
