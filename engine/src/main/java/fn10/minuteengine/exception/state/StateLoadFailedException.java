package fn10.minuteengine.exception.state;

public class StateLoadFailedException extends RuntimeException {
    public StateLoadFailedException(String message) {
        super(message);
    }
}
