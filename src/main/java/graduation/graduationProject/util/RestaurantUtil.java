package graduation.graduationProject.util;


import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.util.exception.IllegalEntryException;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static void validateAdmin(User currentUser) {
        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN)){
            throw new IllegalEntryException("You need admin rights for manipulations");
        }
    }


}