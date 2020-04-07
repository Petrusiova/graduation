package graduation.graduationProject.util;


import graduation.graduationProject.model.Restaurant;
import graduation.graduationProject.model.Role;
import graduation.graduationProject.model.User;
import graduation.graduationProject.util.exception.IllegalEntryException;

import javax.persistence.EntityManager;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static void validateAdmin(EntityManager em, int user_id) {
        User currentUser = (User) em.createNamedQuery(Restaurant.VALIDATE_USER)
                .setParameter("id", user_id)
                .getSingleResult();
        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN)){
            throw new IllegalEntryException("You need admin rights for manipulations");
        }
    }


}