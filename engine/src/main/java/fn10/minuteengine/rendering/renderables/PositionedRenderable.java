package fn10.minuteengine.rendering.renderables;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import fn10.minuteengine.rendering.Tri3;
import fn10.minuteengine.rendering.Vec2;

public abstract class PositionedRenderable extends Renderable {
    @Override
    public Tri3[] getTriangleList() {
        ArrayList<Tri3> tris = new ArrayList<>();
        float[] vertexOffset = getPos().getVertexOffset();
        for (Tri3 tri : getLocalTriangles()) {
            FloatBuffer buf = FloatBuffer.allocate(9);
            for (int i = 0; i < 9; i++) {
                buf.put(vertexOffset[i] + tri.verticies[i]);
            }
            tris.add(new Tri3(tri.colour, buf.array()));
        }
        return tris.toArray(new Tri3[0]);
    }

    public abstract Vec2 getPos();

    public abstract void setPos(Vec2 pos);

    public abstract Tri3[] getLocalTriangles();

}
