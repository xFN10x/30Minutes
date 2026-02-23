package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.MinuteRenderer;
import fn10.minuteengine.rendering.Texture;
import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.rendering.renderables.base.TexturedRenderable;
import fn10.minuteengine.rendering.renderables.base.WorldPositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.TextureShader;
import fn10.minuteengine.util.MinuteRandomUtils;

import org.joml.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.Math;

import static fn10.minuteengine.MinuteEngine.logger;

/**
 * https://stackoverflow.com/questions/19182843/write-text-on-the-screen-with-lwjgl
 */
public class Text extends WorldPositionedRenderable implements TexturedRenderable {

    private  String text;
    private  FontMetrics metrics;
    private BufferedImage bi;
    private Graphics2D graphics;
    private  Vector4d textSize;
    private final Long id = MinuteRandomUtils.getUnqiueId(1);
    private float fontSize;

    public Text(String text, Vector2f pos, Vector2f scale) {
        this(text,pos,scale,32);
    }
    public Text(String text, Vector2f pos, Vector2f scale, float FontSize) {
        super(pos, scale);
        this.text = text;
        this.fontSize = FontSize;
        this.colour = Colour3.BLACK;

        bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        graphics = bi.createGraphics();
        graphics.setFont(MinuteRenderer.defaultFont.deriveFont(FontSize));
        setText(text);
    }

    public void setText(String text) {
        Texture.clearCache(id);
        this.text = text;
        this.metrics = graphics.getFontMetrics();
        Rectangle2D textSizeRect = metrics.getStringBounds(this.text, bi.getGraphics());
        this.textSize = new Vector4d(textSizeRect.getX(), textSizeRect.getY(), textSizeRect.getWidth(), textSizeRect.getHeight());

        bi = new BufferedImage((int) textSize.z, (int) textSize.w, BufferedImage.TYPE_INT_ARGB);
        graphics = bi.createGraphics();
        graphics.setFont(MinuteRenderer.defaultFont.deriveFont(fontSize));
        graphics.setBackground(new Color(50,0,0,0));
        graphics.setColor(Color.WHITE);
        graphics.drawString(this.text, (int) textSize.x, Math.abs((int) textSize.y));
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
        float height = (float) textSize.w();
        float width = (float) textSize.z();
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
