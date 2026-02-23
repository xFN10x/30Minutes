package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.Texture;
import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.rendering.renderables.base.TexturedRenderable;
import fn10.minuteengine.rendering.renderables.base.WorldPositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.TextureShader;
import fn10.minuteengine.util.MinuteRandomUtils;

import org.joml.Vector2f;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static fn10.minuteengine.MinuteEngine.logger;

/**
 * https://stackoverflow.com/questions/19182843/write-text-on-the-screen-with-lwjgl
 */
public class Text extends WorldPositionedRenderable implements TexturedRenderable {

    private final String text;
    private final FontMetrics metrics;
    private BufferedImage bi;
    private Graphics2D graphics;
    private final Rectangle2D textSize;
    private final Long id = MinuteRandomUtils.getUnqiueId(1);

    public Text(String text, Vector2f pos, Vector2f scale) {
        super(pos, scale);
        this.text = text;

        bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        graphics = bi.createGraphics();

        this.colour = Colour3.BLACK;

        //graphics.setFont(MinuteRenderer.defaultFont);
        metrics = graphics.getFontMetrics();
        textSize = metrics.getStringBounds(text, bi.getGraphics());
        logger.info(textSize);

        bi = new BufferedImage((int) textSize.getWidth(), (int) textSize.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = bi.createGraphics();
        //graphics.setFont(MinuteRenderer.defaultFont);
        graphics.setBackground(new Color(50,0,0,0));
        graphics.setColor(Color.WHITE);
        graphics.drawString(text, 0, 12);
    }

    public String getText() {
        return text;
    }

    @Override
    public Vector3ic[] getIndices() {
        return new Vector3ic[]{
                new Vector3i(
                        0, 1, 2
                ),
                new Vector3i(
                        0, 2, 3
                )
        };
    }

    @Override
    public Shader getShader() {
        return Shader.getShader(TextureShader.class);
    }

    @Override
    public VertexArray getLocalVertexArray() {
        float height = (float) textSize.getHeight();
        float width = (float) textSize.getWidth();
        return new VertexArray(
                Colour3.BLACK,
                new Vector2f[]{
                        new Vector2f(width, height), //top-right
                        new Vector2f(0, height), //top-left
                        new Vector2f(0, 0), //bottom-left
                        new Vector2f(width, 0) //bottom-right
                },
                //uv
                new Vector2f[]{
                        new Vector2f(1f, 0f),  //bottom-right
                        new Vector2f(0f, 0f), //bottom-left
                        new Vector2f(0f, 1f), //top-left
                        new Vector2f(1f, 1f), //top-right
                }
        );
    }

    @Override
    public Texture getTexture() {
        return Texture.ofBufferedImage(id, bi);
    }
}
