package graduation.repository;

import graduation.model.Menu;

import java.util.List;

public interface MenuRepository {

    Menu get(int id);

    Menu save(Menu menu);

    boolean delete(int id);

    List<Menu> getAll();
}
