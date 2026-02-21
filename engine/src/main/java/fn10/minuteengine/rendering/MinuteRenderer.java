package fn10.minuteengine.rendering;

import fn10.minuteengine.MinuteEngine;
import fn10.minuteengine.exception.FatalException;
import fn10.minuteengine.rendering.shaders.Shader;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.util.MinuteAssetUtils;
import org.apache.logging.log4j.Level;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import static fn10.minuteengine.MinuteEngine.logger;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.time.Instant;

public final class MinuteRenderer {
    private long currentWindow;
    private final MinuteEngine engine;

    //public ArrayList<Runnable> runPerLoop = new ArrayList<>(0);
    public Vector2i gameSize = new Vector2i(1280, 720);
    public final Font defaultFont;
    private  final FrameRateCounter frc = new FrameRateCounter();


    /**
     * A bool specifing if it's time to render things to screen currently.
     */
    public volatile MinuteRenderQueue renderQueue = new MinuteRenderQueue(this);
    public volatile FloatBuffer vertexBuffer = memAllocFloat(4000000);

    private volatile int GLBuffer;
    private volatile int GLArray;

    public MinuteRenderer(MinuteEngine engine) {
        this.engine = engine;
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

    public void shutdown() {
        glDeleteVertexArrays(GLArray);
        glDeleteBuffers(GLBuffer);
        Shader.closeAllShaders();
    }

    public long createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new FatalException("Unable to create GLFW Window", MinuteEngine.ERR_RENDER_INIT_FAIL);
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW_TRUE);

        currentWindow = GLFW.glfwCreateWindow(gameSize.x(), gameSize.y(), "MinuteEngine", NULL, NULL);
        if (currentWindow == NULL) {
            throw new FatalException("Unable to create GLFW Window", MinuteEngine.ERR_RENDER_INIT_FAIL);
        }

        GLFW.glfwSetKeyCallback(currentWindow, (window, key, scancode, action, mods) -> {
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(currentWindow, pWidth, pHeight);

            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            if (vidmode == null)
                throw new FatalException("Unable to create GLFW Window", MinuteEngine.ERR_RENDER_INIT_FAIL);
            GLFW.glfwSetWindowPos(currentWindow, (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);

            GLFW.glfwMakeContextCurrent(currentWindow);
            //vsync VVV
            //GLFW.glfwSwapInterval(1);
            GLFW.glfwShowWindow(currentWindow);
        }

        return currentWindow;
    }

    public static MinuteRenderer initRenderer(MinuteEngine engine) {
        glfwInit();

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        return new MinuteRenderer(engine);
    }

    /**
     * The rendering loop, runs every rendering frame.
     * <p/>
     * <p/>
     * Calling this function starts the rendering loop. Only call after window
     * creation.
     */
    public void renderLoop(MinuteStateManager state) {
        createCapabilities();

        GLArray = glGenVertexArrays();
        GLBuffer = glGenBuffers();

        glBindVertexArray(GLArray);
        glBindBuffer(GL_ARRAY_BUFFER, GLBuffer);

        glClearColor(0, 0, 0, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        //position
        glVertexAttribPointer(0,3,GL_FLOAT, false, 24, 0);
        glEnableVertexAttribArray(0);

        //colour
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
        glEnableVertexAttribArray(1);

        while (!glfwWindowShouldClose(currentWindow)) {
            Instant begin = Instant.now();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //glEnableClientState(GL_VERTEX_ARRAY);

            renderQueue.shaderVertQueue.forEach((shader, array) -> {
                shader.use();
                vertexBuffer.put(array).flip();
                glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
                glUniform1f(glGetUniformLocation(shader.getProgramID(), "me_Time"), (float) glfwGetTime());
                //glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
                glDrawArrays(GL_TRIANGLES, 0, (vertexBuffer.limit()/3) /2);
            });

            //glDisableClientState(GL_VERTEX_ARRAY);
            // push graphics to the window
            glfwSwapBuffers(currentWindow);

            vertexBuffer.clear();
            glfwPollEvents();
            state.currentState.onRenderThread(renderQueue);
            frc.frame();
            Instant end = Instant.now();
            Instant frameTime = end.minusNanos(begin.getNano());
            //logger.log(Level.INFO, "Frametime: {}", frameTime.getNano()/1000000000f);
            //logger.log(Level.INFO, "Framerate: {}", frc.getFrameRate());
        }
        engine.stop();
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
