package fn10.minuteengine.rendering;

import java.util.ArrayList;

import fn10.minuteengine.rendering.renderables.TriBasedRenderable;

public class MinuteRenderQueue {
    /**
     * A list of renderables that gets cleared every frame.
     */
    protected ArrayList<Renderable> queue = new ArrayList<>(0);

    public void render(Renderable renderable) {
        queue.add(renderable);
    }
}
