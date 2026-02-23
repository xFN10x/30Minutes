package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.MinuteRenderer;
import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.util.MinuteVectorFloatUtils;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector3fc;

public abstract class WorldPositionedRenderable extends Renderable {
    protected final Vector2f scale = new Vector2f();
    protected Vector2f pos = new Vector2f(0, 0);

    public final Vector3fc[] getTriangleVertices() {
        return MinuteVectorFloatUtils.vector2ArrayToVector3Array(getVertexArray().verticies(), 0);
    }

    public final Vector2fc[] getUV() {
        return getVertexArray().UV().toArray(new Vector2f[0]);
    }

    public VertexArray getVertexArray() {
        MinuteRenderer current = MinuteRenderer.current;
        Vector2i gameSize = current.gameSize;
        VertexArray localTriangle = getLocalVertexArray();
        Vector2f dest = new Vector2f();
        pos.div(gameSize.y(),gameSize.y(), dest);
        dest.mul((float) gameSize.x() / 1280, (float) gameSize.y() / 720);
        localTriangle.addOffset(dest);
        localTriangle.verticies().forEach(vector2f -> {
            vector2f.mul(scale.x * ((float) gameSize.y / gameSize.x), scale.y);
        });
        return localTriangle;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public abstract VertexArray getLocalVertexArray();


    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2fc to) {
        scale.set(to);
    }

}
