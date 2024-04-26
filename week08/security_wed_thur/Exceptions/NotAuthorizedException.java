package week08.security_wed_thur.Exceptions;

public class NotAuthorizedException extends ApiException {
    public NotAuthorizedException(int code, String exceptionMessage) {
        super(code, exceptionMessage);
    }
}
