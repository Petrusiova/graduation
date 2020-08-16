package graduation.graduationProject.util;

import graduation.graduationProject.model.Vote;
import graduation.graduationProject.to.VoteTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {

    private VoteUtil() {
    }

    public static List<VoteTo> getTos(Collection<Vote> meals) {
        return meals.stream().map(VoteUtil::createTo).collect(Collectors.toList());
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getName(), vote.getDate());
    }
}
