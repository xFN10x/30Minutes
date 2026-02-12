package fn10.minuteengine.rendering;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import fn10.minuteengine.rendering.renderables.Renderable;
import fn10.minuteengine.state.State;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MinuteRenderer {
    private long currentWindow;

    public ArrayList<Runnable> runPerLoop = new ArrayList<>(0);
    /**
     * A bool specifing if its time to render things to screen currently.
     */
    public volatile boolean inVBlank = false;
    public volatile MinuteRenderQueue renderQueue = new MinuteRenderQueue();

    public long createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        currentWindow = GLFW.glfwCreateWindow(640, 480, "MinuteEngine", NULL, NULL);
        if (currentWindow == NULL) {
            throw new IllegalStateException("Unable to create GLFW Window");
        }

        GLFW.glfwSetKeyCallback(currentWindow, (window, key, scancode, action, mods) -> {
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(currentWindow, pWidth, pHeight);

            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            GLFW.glfwSetWindowPos(currentWindow, (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);

            GLFW.glfwMakeContextCurrent(currentWindow);
            GLFW.glfwSwapInterval(1);
            GLFW.glfwShowWindow(currentWindow);
        }

        return currentWindow;
    }

    public static MinuteRenderer initRenderer() {
        glfwInit();

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        return new MinuteRenderer();
    }

    /**
     * The rendering loop, runs every rendering frame.
     * <p/>
     * <p/>
     * Calling this function starts the rendering loop. Only call after window
     * creation.
     */
    public void renderLoop(State state) {
        GL.createCapabilities();

        GL11.glClearColor(0, 0, 0, 0);

        FloatBuffer vertexBuffer = memAllocFloat(1000);

        while (!glfwWindowShouldClose(currentWindow)) {
            GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glEnableClientState(GL_VERTEX_ARRAY);
            List<Renderable> renderList = Collections.unmodifiableList(renderQueue.queue);
            for (Renderable renderable : renderList) {
                for (Tri3 tri : renderable.getTriangleList()) {
                    glColor3f(tri.colour.r, tri.colour.g, tri.colour.b);
                    vertexBuffer.put(tri.verticies).flip();
                    glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
                    glDrawArrays(GL_TRIANGLES, 0, 3);
                }
            }
            glDisableClientState(GL_VERTEX_ARRAY);
            // push graphics to the window
            glfwSwapBuffers(currentWindow);

            glfwPollEvents();
            state.executeOnRenderThread(renderQueue);
        }
    }
}
