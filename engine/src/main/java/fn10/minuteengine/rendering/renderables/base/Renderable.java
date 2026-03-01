package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.util.MinuteVectorFloatUtils;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector3ic;

import java.awt.*;
import java.nio.FloatBuffer;

public abstract class Renderable {
    protected Color colour = Color.white;
    public static final Color ERROR_PINK = new Color(255,0,255);

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
            buffer.put((float) colour.getRed() /255);
            buffer.put((float) colour.getGreen() /255);
            buffer.put((float) colour.getBlue() /255);
            buffer.put(uvs[i * 2]);
            buffer.put(uvs[(i * 2) + 1]);
        }
        return buffer.array();
    }

    public final void setColour(Color colour) {
        this.colour = colour;
    }

    public final Color getColour() {
        return colour;
    }

    public abstract Shader getShader();
}
//happy birthday jam! 26/02/22