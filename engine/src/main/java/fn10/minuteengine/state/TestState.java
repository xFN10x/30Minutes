package fn10.minuteengine.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.Vec2;
import fn10.minuteengine.rendering.renderables.Triangle;

public class TestState extends State {

    public Vec2 trianglePos = new Vec2(-1, 1);

    @Override
    public void executeOnRenderThread(MinuteRenderQueue queue) {
        queue.render(new Triangle(trianglePos.changeX(0.01f)));
    }

    @Override
    public void executeOnGameUpdate() {
        System.out.println("game update");
    }

}
