package graduation.graduationProject.util.exception;

public class ModificationRestrictionException extends ApplicationException {

    public ModificationRestrictionException() {
        super(ErrorType.VALIDATION_ERROR, "Modification is forbidden");
    }
}