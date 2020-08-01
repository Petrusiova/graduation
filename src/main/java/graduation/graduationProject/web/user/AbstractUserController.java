package graduation.graduationProject.web.user;

import graduation.graduationProject.model.User;
import graduation.graduationProject.repository.datajpa.UserRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.*;


public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository repository;

    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        User user = repository.get(id);
        if (user != null ) {
            return checkNotFoundWithId(user, id);
        }
        else throw new NotFoundException("No user with id " + id + " was found");
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFound(repository.delete(id), "no user with id " + id);
    }

    public void update(User user, int id) {
        Assert.notNull(user, "User cannot be null");
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        repository.save(user);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.getByEmail(email);
    }
}