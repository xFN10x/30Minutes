package fn10.minuteengine.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Triangle;

public class TestState extends State {

    @Override
    public void executeOnRenderThread(MinuteRenderQueue queue) {
        queue.render(new Triangle());
    }

    @Override
    public void executeOnGameUpdate() {
        System.out.println("game update");
    }

}
