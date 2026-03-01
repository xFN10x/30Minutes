package fn10.minuteengine.state;

import fn10.minuteengine.MinuteEngine;
import fn10.minuteengine.audio.MinuteAudioEngine;
import fn10.minuteengine.rendering.MinuteRenderQueue;

import java.awt.*;

public abstract class State {
    protected MinuteAudioEngine audioEngine;
    protected MinuteEngine engine;
    public Color clearColour = Color.BLACK;
    /**
     * This function runs on the render thread, every frame. This is where rendering happens.
     * @param queue the MinuteRenderQueue used to queue rendering renderables.
     * @param dt The delta time / frametime of the previous frame.
     */
    public abstract void onRenderThread(MinuteRenderQueue queue, float dt);

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
