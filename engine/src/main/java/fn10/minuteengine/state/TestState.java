package fn10.minuteengine.state;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.renderables.Square;
import fn10.minuteengine.rendering.renderables.Text;
import org.joml.Vector2f;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Triangle;

import static fn10.minuteengine.MinuteEngine.logger;

public class TestState extends State {
    private final Triangle testTri = new Triangle(new Vector2f(0.5f,0),new Vector2f(100f,100f));
    private final Square testSquare = new Square(new Vector2f(-8200,0),new Vector2f(100f,100f));
    private final Text testText = new Text("", new Vector2f(-8200,2360), new Vector2f(100f,100f), 32);

    @Override
    public void onRenderThread(MinuteRenderQueue queue) {
        queue.render(testTri);
        queue.render(testSquare);
        queue.render(testText);
        //testTri.getPos().add(0,0.1f);
       // testSquare.getPos().add(-0.1f,-0.1f);
        //System.out.println(testText.getPos());
        testText.setText(String.valueOf(queue.renderer.getFrameRateCounter().getFrameRate()));
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
        testTri.setColour(new Colour3(0.99f,0.99f,0.99f));
        testSquare.setColour(new Colour3(0.49f,0.49f,0.99f));
    }

}
