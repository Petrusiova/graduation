package graduation.graduationProject.web.user;


import graduation.graduationProject.HasIdAndEmail;
import graduation.graduationProject.model.User;
import graduation.graduationProject.repository.UserRepository;
import graduation.graduationProject.util.exception.NotFoundException;
import graduation.graduationProject.web.ExceptionInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndEmail user = ((HasIdAndEmail) target);
        User dbUser = null;
        try {
            dbUser = repository.getByEmail(user.getEmail().toLowerCase());
        } catch (NotFoundException e) { }

        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
        }
    }
}
