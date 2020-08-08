package graduation.graduationProject.repository;

import graduation.graduationProject.model.User;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static graduation.graduationProject.util.ValidationUtil.checkNotFound;
import static graduation.graduationProject.util.ValidationUtil.checkNotFoundWithId;


@Repository
public class UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudUserRepository;

    public UserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    public User get(int id) {
        return crudUserRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id = " + id));
    }

    public User getByEmail(String email) {
        return checkNotFound(crudUserRepository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_NAME_EMAIL);
    }

    public User getWithVotes(int id) {
        return checkNotFoundWithId(crudUserRepository.getWithVotes(id), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = checkNotFoundWithId(get(id), id);
        user.setEnabled(enabled);
        crudUserRepository.save(user);
    }

    /**
     * could be used to delete only a new user with no relations
     * Use {@link #enable(int, boolean)} to turn off the opportunity to add meals and votes
     **/
    @CacheEvict(value = "users", allEntries = true)
    public boolean delete(int id) {
        boolean found = crudUserRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
        return true;
    }
}

