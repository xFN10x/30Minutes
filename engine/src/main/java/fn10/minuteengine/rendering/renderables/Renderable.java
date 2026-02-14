package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.MinuteRenderer;
import fn10.minuteengine.rendering.RenderFunction;
import fn10.minuteengine.rendering.Tri3;

/**
 * A renderable is an object that can be rendered on screen. Like some text, or a box for example.
 */
public abstract class Renderable implements RenderFunction {
    public abstract Tri3[] getTriangleList();

    public void onRender(MinuteRenderer renderer) {
        renderer.renderTriangles(getTriangleList());
    }
}
