package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.MinuteRenderer;
import fn10.minuteengine.rendering.RenderFunction;
import fn10.minuteengine.rendering.Tri3;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * A renderable is an object that can be rendered on screen. Like some text, or a box for example.
 */
public abstract class Renderable implements RenderFunction {
    public abstract Tri3[] getTriangleList();

    public void onRender(MinuteRenderer renderer) {
        for (Tri3 tri : getTriangleList()) {
            glColor3f(tri.colour.getRed(), tri.colour.getGreen(), tri.colour.getBlue());
                    renderer.vertexBuffer.put(tri.verticies).flip();
                    glVertexPointer(3, GL_FLOAT, 0, renderer.vertexBuffer);
                    glDrawArrays(GL_TRIANGLES, 0, 3);
        }
    }
}
