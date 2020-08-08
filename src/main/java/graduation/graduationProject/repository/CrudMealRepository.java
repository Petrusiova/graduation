package graduation.graduationProject.repository;

import graduation.graduationProject.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Meal m")
    List<Meal> getAll();

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:id_rest ORDER BY m.date DESC")
    List<Meal> getAllByRestaurant(@Param("id_rest") int id_rest);

    @Query("SELECT m FROM Meal m WHERE m.date=:date ORDER BY m.date DESC")
    List<Meal> getAllByDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:id_rest AND m.date=:date ORDER BY m.date, m.restaurant.id DESC")
    List<Meal> getByRestaurantAndDate(@Param("id_rest") int id_rest, @Param("date") LocalDate date);
}