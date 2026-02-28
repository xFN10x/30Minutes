package fn10.thirtyminutes.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Text;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;
import fn10.thirtyminutes.music.SoundtrackHandler;
import org.joml.Vector2f;

import java.util.Objects;

public class GameLoadState extends State {

    private final String loadingText = "Loading...";
    private final Text text = new Text(loadingText, new Vector2f(-8000, -2512), new Vector2f(100f, 100f), 48);
    private boolean doneLoading = false;

    @Override
    public void onRenderThread(MinuteRenderQueue queue) {
        if (!Objects.equals(text.getText(), loadingText))
            text.setText(loadingText);
        queue.render(text);
        if (doneLoading) {
            text.setColour(text.getColour().darker());
        }
    }

    @Override
    public void onGameUpdate() {

    }

    @Override
    public void onStart() {
        SoundtrackHandler.setDefaultSoundtrack();
        SoundtrackHandler.loadSoundtrack();
        doneLoading = true;
        engine.stateManager.changeState(engine.stateManager.registerState(TestState.class));
    }

    @Override
    public void onLoad() {

    }
}
