package fn10.minuteengine.state;

import org.joml.Vector2f;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Triangle;

public class TestState extends State {

    public Vector2f trianglePos = new Vector2f(-1, 0);

    @Override
    public void executeOnRenderThread(MinuteRenderQueue queue) {
        queue.render(new Triangle(trianglePos.add(0.01f, 0)));
        System.out.println(trianglePos.toString());
    }

    @Override
    public void executeOnGameUpdate() {
        System.out.println("game update");
    }

}
