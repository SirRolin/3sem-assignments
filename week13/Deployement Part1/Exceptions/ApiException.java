package week13.ReactIII.Exceptions;

public class ApiException extends RuntimeException {
    int statusCode;
    Exception exception;
    public ApiException(int code, String exceptionMessage) {
        statusCode = code;
        exception = new Exception(exceptionMessage);
    }
}
