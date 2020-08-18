package graduation.graduationProject;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static graduation.graduationProject.RestaurantTestData.*;
import static graduation.graduationProject.UserTestData.USER;
import static graduation.graduationProject.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsComparator(Vote.class, "user", "restaurant");
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingEquals(VoteTo.class);

    public static final Vote VOTE_1 = new Vote(START_SEQ + 9, LocalDate.of(2020, 4, 6));
    public static final Vote VOTE_2 = new Vote(START_SEQ + 10, LocalDate.of(2020, 4, 5));
    public static final Vote VOTE_3 = new Vote(START_SEQ + 11, LocalDate.of(2020, 4, 6));

    static {
        VOTE_1.setRestaurant(ASTORIA);
        VOTE_2.setRestaurant(ASTORIA);
        VOTE_3.setRestaurant(VICTORIA);
    }

    public static final List<Vote> ALL_RESTS = List.of(VOTE_1, VOTE_2);

    public static final int VOTE_1_ID = START_SEQ + 9;

    public static Vote getNew() {
        Vote vote = new Vote(LocalDate.now());
        vote.setUser(USER);
        vote.setRestaurant(TIFFANY);
        return vote;
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE_1);
        updated.setRestaurant(TIFFANY);
        return updated;
    }
}
