package testData;

import graduation.graduationProject.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class voteControllerTestData {

    public static final Vote VOTE_1 = new Vote(100009,100000,100002, LocalDate.of(2020, 4, 6));
    public static final Vote VOTE_2 = new Vote(100010,100001,100003, LocalDate.of(2020, 4, 6));

    public static final List<Vote> VOTES = Arrays.asList(VOTE_1, VOTE_2);

    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.of();

}
