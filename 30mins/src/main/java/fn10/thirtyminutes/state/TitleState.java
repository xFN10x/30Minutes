package fn10.thirtyminutes.state;

import fn10.minuteengine.audio.Source;
import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.CenteredText;
import fn10.minuteengine.state.State;
import fn10.thirtyminutes.music.SoundtrackHandler;
import org.joml.Vector2f;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static fn10.thirtyminutes.state.GameLoadState.text;

public class TitleState extends State {
    private Source musicSource;
    private final CenteredText madeByText = new CenteredText("Created by _FN10_", new Vector2f(0, 0), new Vector2f(100f, 100f), 64);
    private final CenteredText madeWithText = new CenteredText("Made With MinuteEngine", new Vector2f(0, 0), new Vector2f(100f, 100f), 64);
    private boolean madeWithTextShown = false;
    private final Timer timer = new Timer();

    @Override
    public void onRenderThread(MinuteRenderQueue queue, float dt) {
        if (madeWithTextShown) {
            queue.render(madeWithText);
            float slowdown2 = 5;
            Color colour3 = madeWithText.getColour();
            Color newColour3 = new Color(Math.max((int) (colour3.getRed() * 1f - (dt / slowdown2)), 0),
                    Math.max((int) (colour3.getGreen() * 1f - (dt / slowdown2)), 0),
                    Math.max((int) (colour3.getBlue() * 1f - (dt / slowdown2)), 0),
                    colour3.getAlpha());
            madeWithText.setColour(newColour3);
            madeWithText.clearCached();
        } else {
            queue.render(text);
            queue.render(madeByText);
            Color colour = text.getColour();
            Color newColour = new Color(Math.max((int) (colour.getRed() * 1f - dt), 0),
                    Math.max((int) (colour.getGreen() * 1f - dt), 0),
                    Math.max((int) (colour.getBlue() * 1f - dt), 0),
                    colour.getAlpha());
            text.setColour(newColour);
            text.clearCached();

            float slowdown = 5;
            Color colour2 = madeByText.getColour();
            Color newColour2 = new Color(Math.max((int) (colour2.getRed() * 1f - (dt / slowdown)), 0),
                    Math.max((int) (colour2.getGreen() * 1f - (dt / slowdown)), 0),
                    Math.max((int) (colour2.getBlue() * 1f - (dt / slowdown)), 0),
                    colour2.getAlpha());
            madeByText.setColour(newColour2);
            madeByText.clearCached();
        }
    }

    @Override
    public void onGameUpdate() {

    }

    @Override
    public void onStart() {
        musicSource.play();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                madeWithTextShown = true;
            }
        }, 4000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clearColour = Color.darkGray;
            }
        }, 8000);
    }

    @Override
    public void onLoad() {
        text.setText("Loaded.");

        musicSource = audioEngine.newAudioSource(this);
        musicSource.setAudio(SoundtrackHandler.TITLE_SONG.getAudio());
    }
}
