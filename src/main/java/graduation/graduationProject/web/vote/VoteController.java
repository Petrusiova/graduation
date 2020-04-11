package graduation.graduationProject.web.vote;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.repository.jpa.VoteRepository;
import graduation.graduationProject.util.exception.IllegalTimeException;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.*;

@Controller
public class VoteController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteRepository repository;


    public Vote create(int id_rest) {
        int userId = SecurityUtil.authUserId();
        Vote vote = new Vote(userId, id_rest, LocalDate.now());
        log.info("create {}", vote);
        checkNew(vote);
        return repository.save(vote);
    }

    public Vote update(Vote vote, int id, LocalTime time) {
        Assert.notNull(vote, "Vote cannot be null");
        if (time.isAfter(LocalTime.of(11, 0, 0))){
            throw new IllegalTimeException("You cannot change your vote after 11.00");
        }
        int userId = SecurityUtil.authUserId();

        log.info("update {}", vote);
        if (vote.getUser_id() == userId) {
            assureIdConsistent(vote, id);
            return repository.save(vote);
        }
        else throw new NotFoundException("Do not try to update a vote which doesn't belong to you!");
    }

    public Vote get(int id){
        Vote vote = repository.get(id);
        if (vote != null && vote.getUser_id() == SecurityUtil.authUserId()) {
            return checkNotFoundWithId(vote, id);
        }
        else throw new NotFoundException("No vote with id " + id + " in your list of votes!");
    }

    public List<Vote> getAll(){
        return repository.getAll(SecurityUtil.authUserId());
    }
}
