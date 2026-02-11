package fn10.minuteengine;

import fn10.minuteengine.rendering.MinuteRenderer;

public class MinuteEngine {

    public MinuteRenderer renderer;

    public void start() {
        renderer = MinuteRenderer.initRenderer();
        renderer.createWindow();
    }
}
