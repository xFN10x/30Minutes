package fn10.minuteengine;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import fn10.minuteengine.audio.MinuteAudioEngine;
import fn10.minuteengine.exception.FatalException;
import fn10.minuteengine.game.MinuteGame;
import fn10.minuteengine.state.MinuteStateManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.PluginUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import fn10.minuteengine.game.MinuteGameInfo;
import fn10.minuteengine.logging.MinuteEngineLayout;
import fn10.minuteengine.rendering.MinuteRenderer;
import fn10.minuteengine.util.MinuteJarUtils;

public final class MinuteEngine {

    public static int ERR_GAME_FAIL_LOAD_GENERIC = 10;
    public static int ERR_GAME_FAIL_LOAD_URL_ERROR = 11;
    public static int ERR_GAME_FAIL_LOAD_FAIL_READ = 12;
    public static int ERR_GAME_FAIL_LOAD_INIT_STATE = 13;
    public static int ERR_RENDER_GENERIC = 20;
    public static int ERR_RENDER_INIT_FAIL = 200;
    public static int ERR_NO_GAME_TO_LAUNCH = 91;
    public static int ERR_MALFORMED_LAUNCH_ARG = 92;
    public static int NO_ERR = 0;
    public static int ERR_GENERIC = 1;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private URLClassLoader loader;
    public volatile MinuteGameInfo info;
    public volatile MinuteStateManager stateManager = new MinuteStateManager(this);
    public volatile MinuteAudioEngine audioEngine = new MinuteAudioEngine();

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
        renderer = MinuteRenderer.initRenderer(this);
        running = true;

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.log(Level.FATAL, "Uncaught Exception in " + t.getName(), e);
            if (e instanceof FatalException) {
                exitWithCode(((FatalException) e).crashCode);
            }
            else exitWithCode(ERR_GENERIC);
        });
        mainLoop();
        try {
            Class<?> mainGameClass = loader.loadClass(info.mainClass());
            MinuteGame mainGame = (MinuteGame) mainGameClass.getConstructor().newInstance();
            mainGame.onLoad(stateManager);
            Long stateId = mainGame.getInitialState(stateManager);
            stateManager.changeState(stateId);
        } catch (ClassNotFoundException e) {
            throw new FatalException("Failed to load main game class: " + info.mainClass(), ERR_GAME_FAIL_LOAD_INIT_STATE, e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new FatalException("Failed to load init state.", ERR_GAME_FAIL_LOAD_INIT_STATE,e);
        }
    }

    public void stop() {
        logger.info("Stopping Engine...");
        logger.info("Stopping Renderer...");
        renderer.shutdown();
        exitWithCode(NO_ERR);
    }

    public int loadGameJar(Path jarPath) {
        URL gameInfoJsonUrl;
        try {
            gameInfoJsonUrl = MinuteJarUtils.getFileFromJar(jarPath, "me.game.json");
        } catch (MalformedURLException e) {
            logger.log(Level.ERROR, "Failed to get jar URL with op: " + ", and path " + jarPath + ".", e);
            return ERR_GAME_FAIL_LOAD_URL_ERROR;
        }

        return loadGame(jarPath, gameInfoJsonUrl);
    }

    public int loadGameFromFolders(Path classPath, Path resourcePath) {
        URL gameInfoJsonUrl;
        try {
            gameInfoJsonUrl = resourcePath.resolve("me.game.json").toUri().toURL();
        } catch (MalformedURLException e) {
            logger.log(Level.ERROR, "Fail", e);
            return ERR_GAME_FAIL_LOAD_URL_ERROR;
        }

        return loadGame(classPath, gameInfoJsonUrl);
    }

    private int loadGame(Path classPath, URL gameInfoJsonUrl) {
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
            loader = new URLClassLoader(new URL[]{classPath.toUri().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ERR_GAME_FAIL_LOAD_URL_ERROR;
        }

        logger.info("Loaded game: {}", info.name());
        return NO_ERR;
    }

    /**
     * Starts all the loops in the engine, like rendering.
     */
    private void mainLoop() {
        logger.info("Starting render loop...");
        renderThread.start();
        logger.info("Starting main loop...");
    }

    public static void exitWithCode(int code) {
        logger.info("VM exiting with code: {}", code);
        System.exit(code);
    }

    static void main(String[] args) {
        logger.info("Starting Java VM with args: {}", String.join(", ", args));
        MinuteEngine minuteEngine = new MinuteEngine();
        if (args.length <= 0)
            exitWithCode(ERR_MALFORMED_LAUNCH_ARG);
        else if (args[0].equals("binFolder") && args.length >= 3)  {
            int errCode;
            if ((errCode = minuteEngine.loadGameFromFolders(Path.of(args[1]), Path.of(args[2]))) != NO_ERR)
                exitWithCode(errCode);
        } else if (args[0].equals("jar") && args.length >= 2) {
            int errCode;
            if ((errCode = minuteEngine.loadGameJar(Path.of(args[1]))) != NO_ERR)
                exitWithCode(errCode);
        } else {
            exitWithCode(ERR_MALFORMED_LAUNCH_ARG);
        }


        minuteEngine.start();
    }
}
