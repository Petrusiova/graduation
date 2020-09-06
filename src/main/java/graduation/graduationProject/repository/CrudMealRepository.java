package graduation.graduationProject.repository;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
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
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.restaurant.id=:id_rest")
    int delete(@Param("id") int id, @Param("id_rest") int id_rest);

    @Query("SELECT m FROM Meal m ORDER BY m.date DESC")
    List<Meal> getAll();

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:id_rest ORDER BY m.date DESC")
    List<Meal> getAllByRestaurant(@Param("id_rest") int id_rest);

    @Query("SELECT m FROM Meal m WHERE m.date=:date ORDER BY m.date DESC")
    List<Meal> getAllByDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m JOIN FETCH m.restaurant WHERE m.restaurant.id = :id_rest AND m.date=:date ORDER BY m.date, m.restaurant.id DESC")
    List<Meal> getMealByRestaurantToday(@Param("id_rest") int id_rest, @Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m JOIN FETCH m.restaurant WHERE m.date = ?1")
    List<Meal> getAllToday(LocalDate date);
}