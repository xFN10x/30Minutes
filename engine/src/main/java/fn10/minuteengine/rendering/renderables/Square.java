package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.VertexArray;
import fn10.minuteengine.rendering.renderables.base.PositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.TestShader;
import org.joml.Vector2f;
import org.joml.Vector3i;
import org.joml.Vector3ic;

public class Square extends PositionedRenderable {
    public Square(Vector2f pos) {
        this.pos = pos;
    }

    public VertexArray getLocalVertexArray() {
        return new VertexArray(
                Colour3.WHITE,
                //pos
                new Vector2f[]{
                        new Vector2f(0.5f, 0.5f), //top-right
                        new Vector2f(-0.5f, 0.5f), //top-left
                        new Vector2f(-0.5f, -0.5f), //bottom-left
                        new Vector2f(0.5f, -0.5f) //bottom-right
                },
                //uv
                new Vector2f[]{
                        new Vector2f(1f, 1f),
                        new Vector2f(0f, 1f),
                        new Vector2f(0f, 0f),
                        new Vector2f(1f, 0f)
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
        return Shader.getShader(TestShader.class);
    }
}
