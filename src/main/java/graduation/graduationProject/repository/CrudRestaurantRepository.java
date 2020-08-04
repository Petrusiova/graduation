package graduation.graduationProject.repository;

import graduation.graduationProject.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Restaurant get(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r WHERE r.name=:name")
    Restaurant getByName(@Param("name") String name);
}