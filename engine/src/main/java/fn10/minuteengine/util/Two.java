package fn10.minuteengine.util;

/**
 * A Two is a way to pass multiple objects, in one argument.
 * @param <L> The first type
 * @param <R> The second type
 */
public class Two<L,R> {

    private final L left;
    private final R right;

    public Two(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
