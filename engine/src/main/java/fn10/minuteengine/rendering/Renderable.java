package fn10.minuteengine.rendering;

public interface Renderable {
    /**
     * This function calls every frame, in the rendering phase.
     */
    public void onRender(MinuteRenderer renderer);
}
