package fn10.minuteengine.exception;

public class FatalException extends RuntimeException {
    public final int crashCode;
    public FatalException(String message, int crashCode, Exception cause) {
        super(message, cause);
        this.crashCode = crashCode;
    }
    public FatalException(String message, int crashCode) {
        super(message);
        this.crashCode = crashCode;
    }
}
