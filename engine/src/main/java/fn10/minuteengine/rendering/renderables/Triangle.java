package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.exception.rendering.shaders.ShaderLoadException;
import fn10.minuteengine.rendering.renderables.base.PositionedRenderable;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.rendering.shaders.TestShader;
import org.joml.Vector2f;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.Tri3;

import java.io.IOException;

public class Triangle extends PositionedRenderable {
    public Triangle(Vector2f pos) {
        this.pos = pos;
    }

    @Override
    public Tri3[] getLocalTriangles() {
        return new Tri3[] { new Tri3(Colour3.WHITE, new Vector2f[] { 
            new Vector2f(0.0f, 0.5f),
            new Vector2f(-0.5f, -0.5f),
            new Vector2f(0.5f, -0.5f)}) };
    }

    @Override
    public Shader getShader() {
        return Shader.getShader(TestShader.class);
    }
}
