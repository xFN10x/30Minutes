package fn10.minuteengine.state;

import org.joml.Random;
import org.joml.Vector2f;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Triangle;

public class TestState extends State {

    @Override
    public void onRenderThread(MinuteRenderQueue queue) {
        for (int i = 0; i < 100000; i++) {
            Triangle tri = new Triangle(new Vector2f( (new Random().nextFloat()*2)-1, (new Random().nextFloat()*2)-1));
            tri.setScale(new Vector2f(0.25f,0.25f));
            queue.render(tri);
        }
    }

    @Override
    public void onGameUpdate() {
        System.out.println("game update");
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoad() {

    }

}
