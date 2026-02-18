package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.renderables.base.PositionedRenderable;
import org.joml.Vector2f;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.Tri3;

public class Triangle extends PositionedRenderable {
    public Triangle(Vector2f pos) {
        this.pos = pos;
    }

    @Override
    public Tri3[] getLocalTriangles() {
        return new Tri3[] { new Tri3(Colour3.WHITE, new Vector2f[] { 
            new Vector2f(0.0f, 0.5f),
            new Vector2f(-0.5f, -0.5f),
            new Vector2f(0.5f, -0.5f)}) };
    }
}
