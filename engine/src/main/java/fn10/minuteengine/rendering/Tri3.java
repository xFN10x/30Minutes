package fn10.minuteengine.rendering;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import fn10.minuteengine.util.MinuteVectorUtils;
import org.joml.Vector2f;
import org.joml.Vector2fc;

/**
 * A triangle using 3 floats for each vertex.
 */
public class Tri3 {
    public final Colour3 colour;
    public final ArrayList<Vector2f> verticies;

    public Tri3(Colour3 colour, Vector2f[] verticies) {
        this.colour = colour;
        this.verticies = new ArrayList<Vector2f>(List.of(verticies));
    }

    public Tri3 addOffset(Vector2fc offset) {
        ArrayList<Vector2f> newVerts = new ArrayList<>();
        verticies.forEach(vec -> {
            newVerts.add(vec.add(offset));
        });
        verticies.clear();
        verticies.addAll(newVerts);
        return this;
    }

    /**
     * Creates a new FloatBuffer consisting of all vertices in the given triangles.
     * @param tris The triangles to parse through
     * @return a fliped FloatBuffer of the vertices.
     */
    public static FloatBuffer createVertexBuffer(Tri3... tris) {
        FloatBuffer buf = FloatBuffer.allocate(tris.length*9);
        for (Tri3 tri3 : tris) {
            buf.put(MinuteVectorUtils.vector2ArrayToVertexArray(tri3.verticies, 0));
        }
        return buf.flip();
    }
}
