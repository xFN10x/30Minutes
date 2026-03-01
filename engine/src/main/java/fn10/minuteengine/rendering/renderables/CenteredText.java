package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.VertexArray;
import org.joml.Vector2f;

public class CenteredText extends Text {
    public CenteredText(String text, Vector2f pos, Vector2f scale, float FontSize) {
        super(text, pos, scale, FontSize);
    }

    @Override
    public VertexArray getLocalVertexArray() {
        float height = (float) textSize.w() / 100;
        float width = (float) textSize.z() / 100;
        return new VertexArray(
                ERROR_PINK,
                new Vector2f[]{
                        new Vector2f(width/2, height/2), //top-right
                        new Vector2f(width/-2, height/2), //top-left
                        new Vector2f(width/-2, height/-2), //bottom-left
                        new Vector2f(width/2, height/-2) //bottom-right
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


}
