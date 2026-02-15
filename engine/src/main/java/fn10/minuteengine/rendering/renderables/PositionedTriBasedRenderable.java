package fn10.minuteengine.rendering.renderables;

import org.joml.Vector2f;

import fn10.minuteengine.rendering.Tri3;

public abstract class PositionedTriBasedRenderable extends TriBasedRenderable {
    public Vector2f pos = new Vector2f(0,0);
    @Override
    public Tri3[] getTriangleList() {
        Tri3[] localTriangles = getLocalTriangles();
        for (Tri3 tri : localTriangles) {
            tri.addOffset(pos);
        }
        return localTriangles;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public Tri3[] getLocalTriangles() {
        return null;
    }

}
