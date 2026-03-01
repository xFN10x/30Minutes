package fn10.thirtyminutes.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Text;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;
import fn10.thirtyminutes.ThirtyMinutes;
import fn10.thirtyminutes.music.SoundtrackHandler;
import org.joml.Vector2f;

import java.util.Objects;

public class GameLoadState extends State {

    private static final String loadingText = "Loading...";
    protected static final Text text = new Text(loadingText, new Vector2f(-8000, -2512), new Vector2f(100f, 100f), 48);

    @Override
    public void onRenderThread(MinuteRenderQueue queue, float dt) {
        if (!Objects.equals(text.getText(), loadingText))
            text.setText(loadingText);
        queue.render(text);
    }

    @Override
    public void onGameUpdate() {

    }

    @Override
    public void onStart() {
        SoundtrackHandler.setDefaultSoundtrack();
        SoundtrackHandler.loadSoundtrack();
        engine.stateManager.changeState(ThirtyMinutes.TITLE_STATE);
    }

    @Override
    public void onLoad() {

    }
}
