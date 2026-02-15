package fn10.minuteengine.util;

import java.nio.FloatBuffer;
import java.util.Collection;

import org.joml.Vector2f;

public class MinuteVectorUtils {
    public static float[] vector2ArrayToVertexArray(Collection<Vector2f> vecs, float z) {
        return vector2ArrayToVertexArray(vecs.toArray(new Vector2f[0]), z);
    }

    public static float[] vector2ArrayToVertexArray(Vector2f[] vecs, float z) {
        FloatBuffer buf = FloatBuffer.allocate(9);
        for (Vector2f vec : vecs) {
            buf.put(vec.x);
            buf.put(vec.y);
            buf.put(z);
        }
        return buf.array();
    }
}
