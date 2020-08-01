package graduation.graduationProject.repository.datajpa;

import graduation.graduationProject.model.Vote;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VoteRepository {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.ASC, "date");

private final CrudVoteRepository crudVoteRepository;
private final CrudUserRepository crudUserRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id).filter(item -> userId == item.getUser().getId()).orElse(null);
    }

    public List<Vote> getAll(){
        return crudVoteRepository.findAll(SORT_DATE);
    }

    public List<Vote> getAllByUser(int userId){
        return crudVoteRepository.getAllByUser(userId);
    }

    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }
}
