package fn10.minuteengine.rendering;

import java.util.HashMap;

import fn10.minuteengine.rendering.renderables.base.Renderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.util.MinuteVectorUtils;

public class MinuteRenderQueue {
    private final MinuteRenderer renderer;
    protected final HashMap<Shader, float[]> shaderVertQueue = new HashMap<>();

    public MinuteRenderQueue(MinuteRenderer renderer) {
        this.renderer = renderer;
    }

    public void render(Renderable renderable) {
        shaderVertQueue.put(renderable.getShader(), renderable.getVertexData());
    }
}
