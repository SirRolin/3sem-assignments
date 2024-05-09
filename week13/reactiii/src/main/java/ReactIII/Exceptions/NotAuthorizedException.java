package ReactIII.Exceptions;

public class NotAuthorizedException extends ApiException {
    public NotAuthorizedException(int code, String exceptionMessage) {
        super(code, exceptionMessage);
    }
}
