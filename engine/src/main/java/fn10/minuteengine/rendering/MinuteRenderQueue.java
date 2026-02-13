package fn10.minuteengine.rendering;

import java.util.ArrayList;

import fn10.minuteengine.rendering.renderables.Renderable;

public class MinuteRenderQueue {
    /**
     * A list of renderables that gets cleared every frame.
     */
    protected ArrayList<RenderFunction> queue = new ArrayList<>(0);

    public void render(RenderFunction renderable) {
        queue.add(renderable);
    }
}
