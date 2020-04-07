package graduation.graduationProject.web.user;

import graduation.graduationProject.model.User;
import org.springframework.stereotype.Controller;

import static graduation.graduationProject.web.SecurityUtil.authUserId;


@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}