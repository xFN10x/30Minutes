package fn10.minuteengine.util;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;

public class MinuteVectorIntUtils {
    public static int[] vector2ArrayToInts(Collection<Vector2i> vecs, int z) {
        return vector2ArrayToInts(vecs.toArray(new Vector2i[0]), z);
    }

    public static int[] vector2ArrayToInts(Vector2i[] vecs, int z) {
        IntBuffer buf = IntBuffer.allocate(9);
        for (Vector2i vec : vecs) {
            buf.put(vec.x);
            buf.put(vec.y);
            buf.put(z);
        }
        return buf.array();
    }

    public static Vector3i[] vector2ArrayToVector3Array(Vector2i[] vecs, int z) {
        ArrayList<Vector3i> vec3s = new ArrayList<>();
        for (Vector2i vec : vecs) {
            vec3s.add(new Vector3i(vec, 0));
        }
        return vec3s.toArray(new Vector3i[0]);
    }

    public static Vector3i[] vector2ArrayToVector3Array(Collection<Vector2i> vecs, int z) {
        return vector2ArrayToVector3Array(vecs.toArray(new Vector2i[0]), z);
    }

    public static int[] vector3ArrayToInts(Vector3ic[] vec) {
        IntBuffer buf = IntBuffer.allocate(vec.length*3);
        for (Vector3ic vector3ic : vec) {
            buf.put(vector3ic.x());
            buf.put(vector3ic.y());
            buf.put(vector3ic.z());
        }
        return buf.array();
    }

    public static int[] vector2ArrayToInts(Vector2ic[] vec) {
        IntBuffer buf = IntBuffer.allocate(vec.length*2);
        for (Vector2ic vector2ic : vec) {
            buf.put(vector2ic.x());
            buf.put(vector2ic.y());
        }
        return buf.array();
    }
    
    
}
