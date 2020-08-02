package graduation.graduationProject.repository;

import graduation.graduationProject.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepository implements UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRepository(CrudUserRepository crudUserRepository, PasswordEncoder passwordEncoder) {
        this.crudUserRepository = crudUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        return crudUserRepository.save(user);
    }

    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_NAME_EMAIL);
    }

    public User getWithMeals(int id) {
        return crudUserRepository.getWithVotes(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = crudUserRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}

