package fn10.minuteengine.rendering.renderables.base;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public abstract class ScaledRenderable extends Renderable {
    protected final Vector2f scale = new Vector2f();

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2fc to) {
        scale.set(to);
    }
}
