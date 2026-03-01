package fn10.minuteengine.state;

import fn10.minuteengine.audio.Audio;
import fn10.minuteengine.audio.Source;
import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.Texture;
import fn10.minuteengine.rendering.renderables.Text;
import fn10.minuteengine.rendering.renderables.TexturedSquare;
import fn10.minuteengine.rendering.renderables.Triangle;
import fn10.minuteengine.util.MinuteAssetUtils;
import org.joml.Vector2f;

import java.awt.*;

import static fn10.minuteengine.MinuteEngine.logger;

public class TestState extends State {
    private final Triangle testTri = new Triangle(new Vector2f(0.5f, 0), new Vector2f(100f, 100f));
    private final TexturedSquare testSquare = new TexturedSquare(new Vector2f(-8200, 0), new Vector2f(100f, 100f), Texture.ofTest());
    private final Text testText = new Text("", new Vector2f(-8200, 2360), new Vector2f(100f, 100f), 32);
    private Source source;

    @Override
    public void onRenderThread(MinuteRenderQueue queue, float dt) {
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
        source.play();
    }

    @Override
    public void onLoad() {
        testTri.setColour(new Color(0.99f, 0.99f, 0.99f));
        testSquare.setColour(new Color(0.49f, 0.49f, 0.99f));
        source = audioEngine.newAudioSource(this);
        try {
            Audio audio = Audio.loadAsset(MinuteAssetUtils.getAsset("/test/test.wav", null));
            source.setAudio(audio);
        } catch (Exception e) {
            logger.warn("Failed to load test audio", e);
        }
    }

}
