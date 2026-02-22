package fn10.minuteengine.rendering;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import fn10.minuteengine.util.MinuteVectorFloatUtils;
import org.joml.Vector2f;
import org.joml.Vector2fc;

public record VertexArray(Colour3 colour, ArrayList<Vector2f> verticies, ArrayList<Vector2f> UV) {
    public VertexArray(Colour3 colour, Vector2f[] verticies, Vector2f[] UV) {
        this(colour, new ArrayList<Vector2f>(List.of(verticies)), new ArrayList<>(List.of(UV)));
    }

    public void addOffset(Vector2fc offset) {
        ArrayList<Vector2f> newVerts = new ArrayList<>();
        verticies.forEach(vec -> {
            newVerts.add(vec.add(offset));
        });
        verticies.clear();
        verticies.addAll(newVerts);
        ArrayList<Vector2f> newUV = new ArrayList<>();
        UV.forEach(vec -> {
            newUV.add(vec.add(offset));
        });
        verticies.clear();
        verticies.addAll(newUV);
    }

    /**
     * Creates a new FloatBuffer consisting of all vertices in the given triangles.
     *
     * @param tris The triangles to parse through
     * @return a fliped FloatBuffer of the vertices.
     */
    public static FloatBuffer createVertexBuffer(VertexArray... tris) {
        FloatBuffer buf = FloatBuffer.allocate(tris.length * 9);
        for (VertexArray tri3 : tris) {
            buf.put(MinuteVectorFloatUtils.vector2ArrayToFloats(tri3.verticies, 0));
        }
        return buf.flip();
    }
}
