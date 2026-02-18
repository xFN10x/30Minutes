package fn10.minuteengine.rendering.renderables.base;

import fn10.minuteengine.rendering.MinuteRenderer;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * https://stackoverflow.com/questions/19182843/write-text-on-the-screen-with-lwjgl
 */
public class TextRenderable extends PositionedRenderable {

    private final String text;
    private final Vector2ic textPos;

    public TextRenderable(String text, Vector2ic pos) {
        this.text = text;
        this.textPos = pos;
        this.pos = (org.joml.Vector2f) pos;
    }

    public String getText() {
        return text;
    }

    /*@Override
    public void onRender(MinuteRenderer renderer) {
        Vector2i windowSize = renderer.gameSize;
        BufferedImage image = new BufferedImage(windowSize.x(), windowSize.y(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawString(text, textPos.x(), textPos.y());
    }
*/
}
