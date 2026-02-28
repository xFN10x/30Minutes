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
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.time.Instant;

import static fn10.minuteengine.MinuteEngine.logger;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class MinuteRenderer {
    private long currentWindow;
    private final MinuteEngine engine;

    // public ArrayList<Runnable> runPerLoop = new ArrayList<>(0);
    public Vector2i gameSize = new Vector2i(1280, 720);
    public static Font defaultFont = null;
    public Color clearColour = Color.black;

    private final FrameRateCounter frc = new FrameRateCounter();
    public static MinuteRenderer current;

    /**
     * A bool specifing if it's time to render things to screen currently.
     */
    public volatile MinuteRenderQueue renderQueue = new MinuteRenderQueue(this);
    // public volatile FloatBuffer vertexBuffer = memAllocFloat(4000000);

    private volatile int GLBuffer;
    private volatile int GLArray;
    private volatile int GLElementArray;

    public MinuteRenderer(MinuteEngine engine) {
        this.engine = engine;
        current = this;
    }

    public void shutdown() {
        glDeleteVertexArrays(GLArray);
        glDeleteBuffers(GLBuffer);
        Shader.closeAllShaders();
    }

    public FrameRateCounter getFrameRateCounter() {
        return frc;
    }

    public long createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new FatalException("Unable to create GLFW Window", MinuteEngine.ERR_RENDER_INIT_FAIL);
        }

        GLFW.glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 16);

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
            // vsync VVV
            // GLFW.glfwSwapInterval(1);
            GLFW.glfwShowWindow(currentWindow);
        }

        GLFW.glfwSetWindowSizeCallback(currentWindow, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                gameSize = new Vector2i(width, height);
                glViewport(0, 0, width, height);
            }
        });

        createCapabilities();
        return currentWindow;
    }

    public static MinuteRenderer initRenderer(MinuteEngine engine) {
        glfwInit();

        if (defaultFont == null) {
            Font defaultFont1;
            GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            try {
                InputStream streamAsset = MinuteAssetUtils.getStreamAsset("/font/opensans.ttf", null);
                if (streamAsset == null)
                    throw new NullPointerException("Font Asset Not Found");
                defaultFont1 = Font.createFont(Font.TRUETYPE_FONT, streamAsset).deriveFont(Font.PLAIN, 48);
            } catch (Exception e) {
                logger.log(Level.ERROR, "Failed to load font; using fallback.", e);
                defaultFont1 = localGraphicsEnvironment.getAllFonts()[0];
            }
            defaultFont = defaultFont1;
        }

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

        GLArray = glGenVertexArrays();
        GLBuffer = glGenBuffers();
        GLElementArray = glGenBuffers();

        glBindVertexArray(GLArray);
        glBindBuffer(GL_ARRAY_BUFFER, GLBuffer);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, GLElementArray);

        glClearColor(clearColour.getRed(), clearColour.getGreen(), clearColour.getBlue(), 0);

        // position
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0);
        glEnableVertexAttribArray(0);

        // colour
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 32, 12);
        glEnableVertexAttribArray(1);

        // uv
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 32, 24);
        glEnableVertexAttribArray(2);

        glEnable(GL_MULTISAMPLE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        while (!glfwWindowShouldClose(currentWindow)) {
            Instant begin = Instant.now();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            // glEnableClientState(GL_VERTEX_ARRAY);

            renderQueue.shaderVertQueue.forEach((id, data) -> {
                float[] vertBuffer = data.left();
                int[] indBuffer = data.right().left();
                Shader shader = data.right().right();
                shader.use();
                glBufferData(GL_ARRAY_BUFFER, vertBuffer, GL_DYNAMIC_DRAW);
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, indBuffer, GL_DYNAMIC_DRAW);
                glUniform1f(glGetUniformLocation(shader.getProgramID(), "me_Time"), (float) glfwGetTime());
                if (renderQueue.textures.containsKey(id)) {
                    // this has texture
                    Texture texture = renderQueue.textures.get(id);
                    texture.bind();
                    glUniform1i(glGetUniformLocation(shader.getProgramID(), "tex"), 0);
                }
                glBindVertexArray(GLArray);
                glDrawElements(GL_TRIANGLES, indBuffer.length, GL_UNSIGNED_INT, 0);
            });

            // push graphics to the window
            glfwSwapBuffers(currentWindow);

            renderQueue.shaderVertQueue.clear();
            glfwPollEvents();
            state.currentState.onRenderThread(renderQueue);
            frc.frame();
            Instant end = Instant.now();
            Instant frameTime = end.minusNanos(begin.getNano());
            // logger.log(Level.INFO, "Frametime: {}", frameTime.getNano()/1000000000f);
            // logger.log(Level.INFO, "Framerate: {}", frc.getFrameRate());
        }
        engine.stop();
    }
}
