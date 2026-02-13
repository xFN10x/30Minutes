package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.Tri3;
import fn10.minuteengine.rendering.Vec2;

public class Triangle extends PositionedRenderable {

    private Vec2 pos;
    @Override
    public Vec2 getPos() {
        return pos;
    }

    public Triangle(Vec2 pos) {
        this.pos = pos;
    }

    @Override
    public Tri3[] getLocalTriangles() {
        return new Tri3[] { new Tri3(Colour3.WHITE, new float[] { 
            0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f }) };
    }

    @Override
    public void setPos(Vec2 pos) {
        this.pos = pos;
    }

}
