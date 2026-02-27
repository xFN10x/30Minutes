package fn10.thirtyminutes.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.renderables.Text;
import fn10.minuteengine.state.State;
import org.joml.Vector2f;

public class GameLoadState extends State {

    private String loadingText = "Loading...";
    private final Text text = new Text(loadingText, new Vector2f(0, 0), new Vector2f(100f, 100f));

    @Override
    public void onRenderThread(MinuteRenderQueue queue) {
        queue.render(text);
    }

    @Override
    public void onGameUpdate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoad() {

    }
}
