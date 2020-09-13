package graduation.graduationProject.service;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.repository.CrudRestaurantRepository;
import graduation.graduationProject.repository.CrudUserRepository;
import graduation.graduationProject.repository.CrudVoteRepository;
import graduation.graduationProject.util.exception.ApplicationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class VoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository,
                          CrudUserRepository crudUserRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Vote save(int userId, int restaurant_id) {
        if (get(userId) != null){
            throw new ApplicationException("You cannot change your vote today");
        }
        Vote vote = new Vote();
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurant_id));
        return crudVoteRepository.save(vote);
    }

    public Vote get(int userId) {
        return crudVoteRepository.getMyTodayVote(userId, LocalDate.now());
    }

    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAll(userId);
    }

    public int getVotesCountForRestaurantToday(int restaurant_id) {
        return crudVoteRepository.getVotesForRestaurantToday(restaurant_id, LocalDate.now()).size();
    }

    public boolean delete(int id, int userId) {
        checkNotFoundWithId(crudVoteRepository.delete(id, userId) != 0, id);
        return true;
    }
}
