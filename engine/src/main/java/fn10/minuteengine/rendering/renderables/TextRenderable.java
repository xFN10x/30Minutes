package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.MinuteRenderer;


/**
 * https://stackoverflow.com/questions/19182843/write-text-on-the-screen-with-lwjgl
 */
public class TextRenderable extends PositionedRenderable {

    private final String text;

    public TextRenderable(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void onRender(MinuteRenderer renderer) {
    }

}
