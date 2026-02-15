package fn10.minuteengine.rendering;

import java.util.ArrayList;
import java.util.List;

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
}
