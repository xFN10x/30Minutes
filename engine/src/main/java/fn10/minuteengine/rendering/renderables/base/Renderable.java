package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.util.MinuteVectorUtils;
import org.joml.Vector3fc;

import java.nio.FloatBuffer;

public abstract class Renderable {
    protected Colour3 colour = Colour3.WHITE;
    public abstract Vector3fc[] getTriangleVertices();
    public final float[] getVertexData() {
        float[] triangleVertices = MinuteVectorUtils.vector3ArrayToVertexArray(getTriangleVertices());
        FloatBuffer buffer = FloatBuffer.allocate(triangleVertices.length * 2);
        for (int i = 0; i < triangleVertices.length/3; i++) {
            buffer.put(triangleVertices[i * 3]);
            buffer.put(triangleVertices[(i * 3 ) + 1]);
            buffer.put(triangleVertices[(i * 3 ) + 2]);
            buffer.put(colour.toArray());
        }
        return buffer.array();
    }

    public final void setColour(Colour3 colour) {
        this.colour = colour;
    }

    public final Colour3 getColour() {
        return colour;
    }
    public abstract Shader getShader();
}
