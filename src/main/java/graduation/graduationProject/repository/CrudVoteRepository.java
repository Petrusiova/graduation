package graduation.graduationProject.repository;

import graduation.graduationProject.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id =:user_id ORDER BY v.date DESC")
    List<Vote> getAll(@Param("user_id") int user_id);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id =:restaurant_id AND v.date=:date")
    List<Vote> getVotesForRestaurantToday(@Param("restaurant_id") int restaurant_id, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int user_id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:user_id AND v.date=:date")
    Vote getMyTodayVote(@Param("user_id") int user_id, @Param("date") LocalDate date);
}
