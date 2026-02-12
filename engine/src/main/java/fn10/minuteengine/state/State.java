package fn10.minuteengine.state;

import fn10.minuteengine.rendering.MinuteRenderQueue;

public abstract class State {
    public abstract void executeOnRenderThread(MinuteRenderQueue queue);

    public abstract void executeOnGameUpdate();
}
