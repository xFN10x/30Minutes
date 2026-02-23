package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.util.MinuteVectorFloatUtils;
import org.joml.Vector2f;
import org.joml.Vector2fc;
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
        VertexArray localTriangle = getLocalVertexArray();
        Vector2f dest = new Vector2f();
        pos.div(720,720, dest);
        localTriangle.addOffset(dest);
        localTriangle.verticies().forEach(vector2f -> {
            vector2f.mul(scale.x * ((float) 9 /16), scale.y);
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
