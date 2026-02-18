package fn10.minuteengine.rendering;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import fn10.minuteengine.rendering.renderables.base.Renderable;
import fn10.minuteengine.util.MinuteVectorUtils;

public class MinuteRenderQueue {
    private final MinuteRenderer renderer;
    public MinuteRenderQueue(MinuteRenderer renderer) {
        this.renderer = renderer;
    }
    public void render(Renderable renderable) {
        renderer.vertexBuffer.put(MinuteVectorUtils.vector3ArrayToVertexAray(renderable.getTriangleVerticies()));
    }
}
