package fn10.minuteengine.rendering;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import fn10.minuteengine.state.State;
import fn10.minuteengine.util.MinuteVectorUtils;

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
    public volatile FloatBuffer vertexBuffer = memAllocFloat(1000);

    public long createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        currentWindow = GLFW.glfwCreateWindow(1280, 720, "MinuteEngine", NULL, NULL);
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
            if (vidmode == null)
                throw new IllegalStateException("Unable to create GLFW Window");
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

        while (!glfwWindowShouldClose(currentWindow)) {
            GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glEnableClientState(GL_VERTEX_ARRAY);
            List<Renderable> renderList = Collections.unmodifiableList(renderQueue.queue);
            for (Renderable renderable : renderList) {
                renderable.onRender(this);
            }
            glDisableClientState(GL_VERTEX_ARRAY);
            // push graphics to the window
            glfwSwapBuffers(currentWindow);

            glfwPollEvents();
            state.executeOnRenderThread(renderQueue);
        }
    }

    public void renderTriangles(Tri3... tris) {
        for (Tri3 tri : tris) {
            glColor3f(tri.colour.getRed(), tri.colour.getGreen(), tri.colour.getBlue());
            vertexBuffer.put(MinuteVectorUtils.vector2ArrayToVertexArray(tri.verticies, 0)).flip();
            glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
            glDrawArrays(GL_TRIANGLES, 0, 3);
        }
    }
}
