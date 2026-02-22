package fn10.minuteengine.rendering;

import java.util.HashMap;

import fn10.minuteengine.rendering.renderables.base.Renderable;
import fn10.minuteengine.rendering.renderables.base.TexturedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.util.MinuteRandomUtils;
import fn10.minuteengine.util.MinuteVectorFloatUtils;
import fn10.minuteengine.util.MinuteVectorIntUtils;
import fn10.minuteengine.util.Two;

public class MinuteRenderQueue {
    private final MinuteRenderer renderer;
    protected final HashMap<Long, Two<float[], Two<int[], Shader>>> shaderVertQueue = new HashMap<>();
    protected final HashMap<Long, Texture> textures = new HashMap<>();

    public MinuteRenderQueue(MinuteRenderer renderer) {
        this.renderer = renderer;
    }

    public void render(Renderable renderable) {
        Long unqiueId = MinuteRandomUtils.getUnqiueId(0);
        shaderVertQueue.put(
                unqiueId
                , new Two<>(
                        renderable.getVertexData(),
                        new Two<>(
                                MinuteVectorIntUtils.vector3ArrayToInts(
                                        renderable.getIndices()

                                ),
                                renderable.getShader()
                        )));
        if (renderable instanceof TexturedRenderable) {
            textures.put(unqiueId, ((TexturedRenderable) renderable).getTexture());
        }

    }
}
