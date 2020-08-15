package graduation.graduationProject.web.user;

import graduation.graduationProject.model.AbstractBaseEntity;
import graduation.graduationProject.model.User;
import graduation.graduationProject.repository.UserRepository;
import graduation.graduationProject.to.UserTo;
import graduation.graduationProject.util.UserUtil;
import graduation.graduationProject.util.exception.ModificationRestrictionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.assureIdConsistent;
import static graduation.graduationProject.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniqueMailValidator emailValidator;

//    private boolean modificationRestriction;
//
//    @Autowired
//    @SuppressWarnings("deprecation")
//    public void setEnvironment(Environment environment) {
//        modificationRestriction = environment.acceptsProfiles("heroku");
//    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public List<User> getAll() {
        log.info("getAll");
        return userRepository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    public User create(UserTo userTo) {
        log.info("create from to {}", userTo);
        return create(UserUtil.createNewFromTo(userTo));
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return userRepository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkModificationAllowed(id);
        userRepository.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        checkModificationAllowed(id);
        userRepository.save(user);
    }

//    public void update(UserTo userTo, int id) {
//        log.info("update {} with id={}", userTo, id);
//        assureIdConsistent(userTo, id);
//        checkModificationAllowed(id);
//        userRepository.save(userTo);
//    } todo fix it <----

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return userRepository.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        checkModificationAllowed(id);
        userRepository.enable(id, enabled);
    }

    private void checkModificationAllowed(int id) {  // TODO: 15.08.2020 fix it <----
        if (
//                modificationRestriction &&
                id < AbstractBaseEntity.START_SEQ + 2) {
            throw new ModificationRestrictionException();
        }
    }
}