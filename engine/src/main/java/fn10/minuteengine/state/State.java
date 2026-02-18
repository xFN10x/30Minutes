package fn10.minuteengine.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;
import fn10.minuteengine.rendering.MinuteRenderer;

public abstract class State {

    protected boolean loadOnGameLoad = false;

    /**
     * Load this state
     */
    protected final void loadOnGameLoad() {
        loadOnGameLoad = true;
    }

    /**
     * This function runs on the render thread, every frame. This is where rendering happens.
     * @param queue the MinuteRenderQueue used to queue rendering renderables.
     */
    public abstract void onRenderThread(MinuteRenderQueue queue);

    /**
     * Executes every game update.
     */
    public abstract void onGameUpdate();
    /**
     * Executes when the state starts.
     */
    public abstract void onStart();
    /**
     * Executes when the state is loading for the first time.
     */
    public abstract void onLoad();
}
