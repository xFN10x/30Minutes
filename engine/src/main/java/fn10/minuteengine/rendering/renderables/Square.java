package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.rendering.renderables.base.WorldPositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.SolidColourShader;
import org.joml.Vector2f;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.awt.*;

public class Square extends WorldPositionedRenderable {
    public Square(Vector2f Position, Vector2f Scale) {
        super(Position, Scale);
    }

    public VertexArray getLocalVertexArray() {
        return new VertexArray(
                Color.white,
                //pos
                new Vector2f[]{
                        new Vector2f(1, 1), //top-right
                        new Vector2f(0, 1), //top-left
                        new Vector2f(0, 0), //bottom-left
                        new Vector2f(1, 0) //bottom-right
                });
    }

    @Override
    public Vector3ic[] getIndices() {
        return new Vector3ic[] {
                new Vector3i(
                        0,1,2
                ),
                new Vector3i(
                        0,2,3
                )
        };
    }

    @Override
    public Shader getShader() {
        return Shader.getShader(SolidColourShader.class);
    }
}
