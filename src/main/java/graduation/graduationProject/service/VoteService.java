package graduation.graduationProject.service;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.repository.CrudRestaurantRepository;
import graduation.graduationProject.repository.CrudUserRepository;
import graduation.graduationProject.repository.CrudVoteRepository;
import graduation.graduationProject.util.exception.ModificationRestrictionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public VoteService(CrudVoteRepository crudVoteRepository,
                       CrudUserRepository crudUserRepository,
                       CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Vote create(int userId, int restaurant_id) {
        if (isAfterEleven() && get(userId) != null) {
            throw new ModificationRestrictionException();
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
        if (isAfterEleven()) {
            throw new ModificationRestrictionException();
        }
        checkNotFoundWithId(crudVoteRepository.delete(id, userId) != 0, id);
        return true;
    }

    private boolean isAfterEleven() {
        return LocalTime.now().isAfter(LocalTime.of(11, 0));
    }


}
