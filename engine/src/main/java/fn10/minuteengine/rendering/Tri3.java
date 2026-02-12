package fn10.minuteengine.rendering;

/**
 * A triangle using 3 floats for each vertex.
 */
public class Tri3 {
    public final Colour3 colour;
    public final float[] verticies;

    public Tri3(Colour3 colour, float[] verticies) {
        this.colour = colour;
        this.verticies = verticies;
    }
}
