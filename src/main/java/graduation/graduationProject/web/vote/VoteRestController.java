package graduation.graduationProject.web.vote;

import graduation.graduationProject.AuthorizedUser;
import graduation.graduationProject.model.Vote;
import graduation.graduationProject.repository.VoteRepository;
import graduation.graduationProject.to.VoteTo;
import graduation.graduationProject.util.VoteUtil;
import graduation.graduationProject.util.exception.ModificationRestrictionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.assureIdConsistent;
import static graduation.graduationProject.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    public static final String REST_URL = "/rest/profile/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get meal {}", id);
        return voteRepository.get(id, authUser.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete meal {}", id);
        checkModificationAllowed();
        voteRepository.delete(id, authUser.getId());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAll");
        return VoteUtil.getTos(voteRepository.getAll(authUser.getId()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Vote vote, @PathVariable int id, @RequestParam int restaurant_id,
                       @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        assureIdConsistent(vote, id);
        log.info("update {} for user {}", vote, userId);
        checkModificationAllowed();
        voteRepository.save(vote, userId, restaurant_id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Validated @RequestBody Vote vote, @RequestParam int restaurant_id,
                                                   @AuthenticationPrincipal AuthorizedUser authUser) {
        checkNew(vote);
        int userId = authUser.getId();
        log.info("create {} for restaurant {} and user {}", vote, restaurant_id, userId);
        checkModificationAllowed();
        Vote created = voteRepository.save(vote, userId, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private void checkModificationAllowed(){
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))){
            throw new ModificationRestrictionException();
        }
    }
}
