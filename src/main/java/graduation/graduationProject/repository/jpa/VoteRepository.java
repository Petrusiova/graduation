package graduation.graduationProject.repository.jpa;

import graduation.graduationProject.model.Vote;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class VoteRepository {

    @PersistenceContext
    private EntityManager em;

    public Vote save(Vote vote) {
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else {
            return em.merge(vote);
        }
    }

    @Transactional(readOnly = true)
    public Vote get(int id){
        return em.find(Vote.class, id);
    }

    public List<Vote> getAll(int user_id){
        return em.createNamedQuery(Vote.GET_ALL)
                .setParameter("user_id", user_id)
                .getResultList();
    }
}
