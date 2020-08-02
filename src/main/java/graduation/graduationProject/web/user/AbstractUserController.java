package graduation.graduationProject.web.user;

import graduation.graduationProject.model.User;
import graduation.graduationProject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.*;


public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        log.info("getAll");
        return userRepository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        userRepository.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return userRepository.getByEmail(email);
    }
}