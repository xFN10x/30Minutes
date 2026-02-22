package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.util.MinuteVectorFloatUtils;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3fc;

public abstract class PositionedRenderable extends ScaledRenderable {
    public Vector2f pos = new Vector2f(0, 0);

    public final Vector3fc[] getTriangleVertices() {
        return MinuteVectorFloatUtils.vector2ArrayToVector3Array(getVertexArray().verticies(), 0);
    }

    public final Vector2fc[] getUV() {
        return getVertexArray().UV().toArray(new Vector2f[0]);
    }

    public VertexArray getVertexArray() {
        VertexArray localTriangle = getLocalVertexArray();
        localTriangle.addOffset(pos);
        localTriangle.verticies().forEach(vector2f -> {
            vector2f.mul(scale);
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

}
