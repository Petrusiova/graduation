package graduation.graduationProject.web.vote;

import graduation.graduationProject.AuthorizedUser;
import graduation.graduationProject.model.Vote;
import graduation.graduationProject.service.VoteService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    public static final String REST_URL = "/rest/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @GetMapping("/mineToday")
    public Vote get(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get today vote for user {}", authUser);
        return voteService.get(authUser.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        if (isAfterEleven()) {
            throw new ModificationRestrictionException();
        }
        log.info("delete vote {}", id);
        voteService.delete(id, authUser.getId());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAll");
        return VoteUtil.getTos(voteService.getAll(authUser.getId()));
    }

    @GetMapping("/todayByRestaurant")
    public int getVotesForRestaurantToday(@RequestParam int restaurant_id) {
        log.info("get votes count for restaurant {} today", restaurant_id);
        return voteService.getVotesCountForRestaurantToday(restaurant_id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurant_id,
                                                   @AuthenticationPrincipal AuthorizedUser authUser) {
        if (isAfterEleven() && get(authUser) != null) {
            throw new ModificationRestrictionException();
        }
        int userId = authUser.getId();
        log.info("create vote for restaurant {} and user {}", restaurant_id, userId);
        Vote created = voteService.create(userId, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private boolean isAfterEleven() {
        return LocalTime.now().isAfter(LocalTime.of(11, 0));
    }
}
