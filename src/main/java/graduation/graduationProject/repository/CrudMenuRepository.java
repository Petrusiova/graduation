package graduation.graduationProject.repository;

import graduation.graduationProject.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:id_rest ORDER BY m.date DESC")
    List<Menu> getAllByRestaurant(@Param("id_rest") int id_rest);

    @Query("SELECT m FROM Menu m WHERE m.date=:date ORDER BY m.date DESC")
    List<Menu> getAllByDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:id_rest AND m.date=:date ORDER BY m.date, m.restaurant.id DESC")
    List<Menu> getByRestaurantAndDate(@Param("id_rest") int id_rest, @Param("date") LocalDate date);
}