package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.Tri3;
import fn10.minuteengine.util.MinuteVectorUtils;
import org.joml.Vector2f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

public abstract class PositionedRenderable extends ScaledRenderable {
    public Vector2f pos = new Vector2f(0, 0);

    @Override
    public Vector3fc[] getTriangleVerticies() {
        Tri3[] triangleList = getTriangleList();
        //apparently specificing a length is faster
        ArrayList<Vector3fc> vec3s = new ArrayList<>(triangleList.length * 3);
        for (Tri3 tri3 : triangleList) {
            vec3s.addAll(List.of(MinuteVectorUtils.vector2ArrayToVector3Array(tri3.verticies(), 0)));
        }
        return vec3s.toArray(new Vector3fc[0]);
    }

    public Tri3[] getTriangleList() {
        Tri3[] localTriangles = getLocalTriangles();
        for (Tri3 tri : localTriangles) {
            tri.addOffset(pos);
            tri.verticies().forEach(vector2f -> {
                vector2f.mul(scale);
            });
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
