package graduation.graduationProject.util.exception;

public class NotFoundException extends ApplicationException {

    //  http://stackoverflow.com/a/22358422/548473
    public NotFoundException(String arg) {
        super(ErrorType.DATA_NOT_FOUND, "not Found", arg);
    }
}