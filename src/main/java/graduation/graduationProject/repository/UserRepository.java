package graduation.graduationProject.repository;

import graduation.graduationProject.model.User;
import graduation.graduationProject.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

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

    public User save(User user) {
        return crudUserRepository.save(user);
    }

    public boolean delete(int id) {
        boolean found = crudUserRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
        return true;
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
}

