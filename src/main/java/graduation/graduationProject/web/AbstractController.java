package graduation.graduationProject.web;

import graduation.graduationProject.model.AbstractBaseEntity;
import graduation.graduationProject.util.exception.ModificationRestrictionException;

public class AbstractController {
    protected void checkModificationAllowed(int id) {
//        if (id < AbstractBaseEntity.START_SEQ + 2) {
//            throw new ModificationRestrictionException();
//        }
    }
}
