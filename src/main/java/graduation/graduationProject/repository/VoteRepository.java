package graduation.graduationProject.repository;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public Vote save(Vote vote, int userId, int id_rest) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(id_rest));
        return crudVoteRepository.save(vote);
    }

    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElseThrow(() -> new NotFoundException("No vote with id = " + id + " and user's id = " + userId));
    }

    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAll(userId);
    }

    public boolean delete(int id, int userId) {
        checkNotFoundWithId(crudVoteRepository.delete(id, userId) != 0, id);
        return true;
    }

    public Vote getWithRestaurant(int id, int id_rest, int userId) {
        return checkNotFoundWithId(crudVoteRepository.getWithRestaurant(id, id_rest, userId), id);
    }

    public Vote getWithUser(int id, int userId) {
        return checkNotFoundWithId(crudVoteRepository.getWithUser(id, userId), id);
    }
}
