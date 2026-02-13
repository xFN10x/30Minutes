package fn10.minuteengine.rendering;

public class Vec2 {
    protected float x;
    protected float y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 changeX(float toChange) {
        x += toChange;
        return this;
    }

    public Vec2 changeY(float toChange) {
        y += toChange;
        return this;
    }

    public float[] getVertexOffset() {
        return new float[] {
                x, y, 0,
                x, y, 0,
                x, y, 0
        };
    }
}
