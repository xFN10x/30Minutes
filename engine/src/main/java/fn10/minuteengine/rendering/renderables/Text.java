package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.exception.rendering.shaders.ShaderLoadException;
import fn10.minuteengine.rendering.renderables.base.PositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.TestShader;
import org.joml.Vector2ic;

import java.io.IOException;

/**
 * https://stackoverflow.com/questions/19182843/write-text-on-the-screen-with-lwjgl
 */
public class Text extends PositionedRenderable {

    private final String text;
    private final Vector2ic textPos;

    public Text(String text, Vector2ic pos) {
        this.text = text;
        this.textPos = pos;
        this.pos = (org.joml.Vector2f) pos;
    }

    public String getText() {
        return text;
    }

    @Override
    public Shader getShader() {
        return Shader.getShader(TestShader.class);
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
