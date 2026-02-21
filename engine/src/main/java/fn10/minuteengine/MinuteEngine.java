package fn10.minuteengine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import fn10.minuteengine.state.MinuteStateManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.PluginUtil;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import fn10.minuteengine.game.MinuteGameInfo;
import fn10.minuteengine.logging.MinuteEngineLayout;
import fn10.minuteengine.rendering.MinuteRenderer;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;
import fn10.minuteengine.util.MinuteJarUtils;

public final class MinuteEngine {

    public static int ERR_GAME_FAIL_LOAD_GENERIC = 10;
    public static int ERR_GAME_FAIL_LOAD_URL_ERROR = 11;
    public static int ERR_GAME_FAIL_LOAD_FAIL_READ = 12;
    public static int ERR_GENERIC = 90;
    public static int ERR_NO_GAME_TO_LAUNCH = 91;
    public static int NO_ERR = -1;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private URLClassLoader loader;
    public volatile MinuteGameInfo info;
    public volatile MinuteStateManager stateManager = new MinuteStateManager(this);

    public MinuteRenderer renderer;

    static {
        PluginUtil.instantiatePlugin(MinuteEngineLayout.class);
    }

    public static Logger logger = LogManager.getLogger("MinuteEngine");

    public Thread renderThread = new Thread(() -> {
        renderer.createWindow();
        renderer.renderLoop(stateManager);
    }, "ME-Render");

    private boolean running = false;

    public void start() {
        renderer = MinuteRenderer.initRenderer();
        running = true;
        stateManager.changeState(stateManager.registerState(TestState.class));
        renderThread.setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
        });
        mainLoop();
    }

    public int loadGameJar(Path jarPath, String op) {
        URL gameInfoJsonUrl;
        try {
            if (op.equals("jar"))
                gameInfoJsonUrl = MinuteJarUtils.getFileFromJar(jarPath, "me.game.json");
            else if (op.equals("binFolder"))
                gameInfoJsonUrl = jarPath.resolve("me.game.json").toUri().toURL();
            else
                return ERR_GAME_FAIL_LOAD_URL_ERROR;
        } catch (MalformedURLException e) {
            logger.log(Level.ERROR, "Failed to get jar URL with op: " + op + ", and path " + jarPath + ".", e);
            return ERR_GAME_FAIL_LOAD_URL_ERROR;
        }

        logger.info("Loading game from: {}", gameInfoJsonUrl.toString());

        MinuteGameInfo gameInfo;
        try (InputStream jsonStream = gameInfoJsonUrl.openStream()) {
            gameInfo = gson.fromJson(new String(jsonStream.readAllBytes()), MinuteGameInfo.class);
        } catch (JsonSyntaxException | IOException e) {
            //e.printStackTrace();
            logger.log(Level.ERROR, "Failed to load game " + gameInfoJsonUrl + "'s json file.", e);
            return ERR_GAME_FAIL_LOAD_FAIL_READ;
        }

        info = gameInfo;

        try {
            loader = new URLClassLoader(new URL[]{jarPath.toUri().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ERR_GAME_FAIL_LOAD_URL_ERROR;
        }

        logger.info("Loaded game: {}", info.name());
        return -1;
    }

    /**
     * Starts all the loops in the engine, like rendering.
     */
    private void mainLoop() {
        renderThread.start();
    }

    public static void exitWithCode(int code) {
        logger.info("VM exiting with code: {}", code);
        System.exit(code);
    }

    static void main(String[] args) {
        logger.info("Starting Java VM with args: {}", String.join(", ", args));
        if (args.length <= 0)
            exitWithCode(ERR_NO_GAME_TO_LAUNCH);
        String jarOp = "jar";
        if (args.length >= 2) {
            jarOp = args[1];
        }
        Path jarPath = Path.of(args[0]);
        MinuteEngine minuteEngine = new MinuteEngine();
        int errCode;
        if ((errCode = minuteEngine.loadGameJar(jarPath, jarOp)) != NO_ERR)
            exitWithCode(errCode);

        minuteEngine.start();
    }
}
