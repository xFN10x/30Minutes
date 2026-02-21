package fn10.minuteengine.state;

import fn10.minuteengine.rendering.Colour3;
import org.joml.Vector2f;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Triangle;

public class TestState extends State {
    private final Triangle testTri = new Triangle(new Vector2f(0,0));

    @Override
    public void onRenderThread(MinuteRenderQueue queue) {
        queue.render(testTri);
        testTri.getPos().add(0,0.00001f);
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
        testTri.setScale(new Vector2f(0.25f,0.25f));
        testTri.setColour(new Colour3(0.99f,0.99f,0.99f));
    }

}
