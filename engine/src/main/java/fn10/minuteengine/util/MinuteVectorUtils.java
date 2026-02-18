package fn10.minuteengine.util;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

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

    public static Vector3f[] vector2ArrayToVector3Array(Vector2f[] vecs, float z) {
        ArrayList<Vector3f> vec3s = new ArrayList<>();
        for (Vector2f vec : vecs) {
            vec3s.add(new Vector3f(vec, 0));
        }
        return vec3s.toArray(new Vector3f[0]);
    }

    public static Vector3f[] vector2ArrayToVector3Array(Collection<Vector2f> vecs, float z) {
        return vector2ArrayToVector3Array(vecs.toArray(new Vector2f[0]), z);
    }

    public static float[] vector3ArrayToVertexAray(Vector3fc[] vec) {
        FloatBuffer buf = FloatBuffer.allocate(vec.length*3);
        for (Vector3fc vector3fc : vec) {
            buf.put(vector3fc.x());
            buf.put(vector3fc.y());
            buf.put(vector3fc.z());
        }
        return buf.array();
    }
}
