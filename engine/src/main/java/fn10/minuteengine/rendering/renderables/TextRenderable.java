package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.MinuteRenderer;

import java.awt.image.BufferedImage;

/**
 * https://stackoverflow.com/questions/19182843/write-text-on-the-screen-with-lwjgl
 */
public class TextRenderable extends PositionedTriBasedRenderable {

    private final String text;

    public TextRenderable(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void onRender(MinuteRenderer renderer) {
        BufferedImage image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
    }

}
