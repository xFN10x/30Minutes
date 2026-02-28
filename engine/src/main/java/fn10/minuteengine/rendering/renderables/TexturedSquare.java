package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.Texture;
import fn10.minuteengine.rendering.renderables.base.TexturedRenderable;
import org.joml.Vector2f;

public class TexturedSquare extends Square implements TexturedRenderable {
    private final Texture tex;

    public TexturedSquare(Vector2f Position, Vector2f Scale, Texture tex) {
        super(Position, Scale);
        this.tex = tex;
    }

    @Override
    public Texture getTexture() {
        return tex;
    }
}
