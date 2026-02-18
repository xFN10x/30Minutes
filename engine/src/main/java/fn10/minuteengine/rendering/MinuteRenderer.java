package fn10.minuteengine.rendering;

import fn10.minuteengine.rendering.renderables.base.Renderable;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.util.MinuteAssetUtils;
import org.apache.logging.log4j.Level;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import fn10.minuteengine.util.MinuteVectorUtils;

import static fn10.minuteengine.MinuteEngine.logger;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;

public final class MinuteRenderer {
    private long currentWindow;

    //public ArrayList<Runnable> runPerLoop = new ArrayList<>(0);
    public Vector2i gameSize = new Vector2i(1280, 720);
    public final Font defaultFont;
    private  final FrameRateCounter frc = new FrameRateCounter();


    /**
     * A bool specifing if it's time to render things to screen currently.
     */
    public volatile boolean inVBlank = false;
    public volatile MinuteRenderQueue renderQueue = new MinuteRenderQueue(this);
    public volatile FloatBuffer vertexBuffer = memAllocFloat(4000000);

    public MinuteRenderer() {
        Font defaultFont1;
        try {
            InputStream streamAsset = MinuteAssetUtils.getStreamAsset("/font/opensans.ttf", null);
            if (streamAsset == null) throw new NullPointerException("Font Asset Not Found");
            defaultFont1 = Font.createFont(Font.TRUETYPE_FONT, streamAsset);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Failed to load font; using fallback.");
            defaultFont1 = Font.getFont(Font.SANS_SERIF);
        }
        defaultFont = defaultFont1;
    }

    public long createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW_TRUE);

        currentWindow = GLFW.glfwCreateWindow(gameSize.x(), gameSize.y(), "MinuteEngine", NULL, NULL);
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
            //GLFW.glfwSwapInterval(1);
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
    public void renderLoop(MinuteStateManager state) {
        GL.createCapabilities();

        GL11.glClearColor(0, 0, 0, 0);

        while (!glfwWindowShouldClose(currentWindow)) {
            Instant begin = Instant.now();
            GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glEnableClientState(GL_VERTEX_ARRAY);

            vertexBuffer.flip();
            glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
            glDrawArrays(GL_TRIANGLES, 0, vertexBuffer.limit()/3);

            glDisableClientState(GL_VERTEX_ARRAY);
            // push graphics to the window
            glfwSwapBuffers(currentWindow);

            vertexBuffer.clear();
            glfwPollEvents();
            state.currentState.onRenderThread(renderQueue);
            frc.frame();
            Instant end = Instant.now();
            Instant frameTime = end.minusNanos(begin.getNano());
            logger.log(Level.INFO, "Frametime: {}", frameTime.getNano()/1000000000f);
            logger.log(Level.INFO, "Framerate: {}", frc.getFrameRate());
        }
    }

    public final class Renderer2D {
          private BufferedImage buffer = null;

          public BufferedImage createBuffer(int width, int height) {
              buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
              buffer.createGraphics();
              return buffer;
          }
    }
}
