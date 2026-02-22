package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.util.MinuteVectorFloatUtils;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector3ic;

import java.nio.FloatBuffer;

public abstract class Renderable {
    protected Colour3 colour = Colour3.WHITE;

    public abstract Vector3fc[] getTriangleVertices();
    public abstract Vector2fc[] getUV();
    public abstract Vector3ic[] getIndices();

    public final float[] getVertexData() {
        float[] vertexPoses = MinuteVectorFloatUtils.vector3ArrayToFloats(getTriangleVertices());
        float[] uvs = MinuteVectorFloatUtils.vector2ArrayToFloats(getUV());
        int vertexCount = vertexPoses.length / 3;
        FloatBuffer buffer = FloatBuffer.allocate(
                (vertexCount * 3) + //pos
                        (vertexCount * 3) + //colour
                        (vertexCount * 2) //uv
        );

        for (int i = 0; i < vertexCount; i++) {
            buffer.put(vertexPoses[i * 3]);
            buffer.put(vertexPoses[(i * 3) + 1]);
            buffer.put(vertexPoses[(i * 3) + 2]);
            buffer.put(colour.toArray());
            buffer.put(uvs[i * 2]);
            buffer.put(uvs[(i * 2) + 1]);
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
