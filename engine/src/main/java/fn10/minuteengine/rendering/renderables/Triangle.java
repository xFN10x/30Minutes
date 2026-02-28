package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.renderables.base.WorldPositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.SolidColourShader;
import org.joml.Vector2f;

import fn10.minuteengine.rendering.VertexArray;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.awt.*;

public class Triangle extends WorldPositionedRenderable {
    public Triangle(Vector2f Position, Vector2f Scale) {
        super(Position, Scale);
    }

    public VertexArray getLocalVertexArray() {
        return new VertexArray(
                Color.white,
                //pos
                new Vector2f[]{
                        new Vector2f(0.5f, 0.5f),
                        new Vector2f(0, 0),
                        new Vector2f(1, 0)}
        );
    }

    @Override
    public Vector3ic[] getIndices() {
        return new Vector3ic[] {
                new Vector3i(
                        0,1,2
                )
        };
    }

    @Override
    public Shader getShader() {
        return Shader.getShader(SolidColourShader.class);
    }
}
