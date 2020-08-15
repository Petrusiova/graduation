package graduation.graduationProject.web;

import graduation.graduationProject.model.AbstractBaseEntity;
import graduation.graduationProject.util.exception.ModificationRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class AbstractController {

    private boolean modificationRestriction;

    @Autowired
    @SuppressWarnings("deprecation")
    public void setEnvironment(Environment environment) {
        modificationRestriction = environment.acceptsProfiles("heroku");
    }

    protected void checkModificationAllowed(int id) {
        if (modificationRestriction && id < AbstractBaseEntity.START_SEQ + 2) {
            throw new ModificationRestrictionException();
        }
    }
}
