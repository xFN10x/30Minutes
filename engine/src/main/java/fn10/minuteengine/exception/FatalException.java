package fn10.minuteengine.exception;

public class FatalException extends RuntimeException {
    protected final int crashCode;
    public FatalException(String message, int crashCode) {
        super(message);
        this.crashCode = crashCode;
    }
}
