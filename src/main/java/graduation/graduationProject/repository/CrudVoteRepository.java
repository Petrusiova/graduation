package graduation.graduationProject.repository;

import graduation.graduationProject.model.Meal;
import graduation.graduationProject.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id =:user_id ORDER BY v.date DESC")
    List<Vote> getAll(@Param("user_id") int user_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int user_id);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id = ?1 and v.restaurant.id = ?2 and v.user.id = ?3")
    Vote getWithRestaurant(int id, int id_rest, int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = ?1 and v.user.id = ?2")
    Vote getWithUser(int id, int userId);
}
