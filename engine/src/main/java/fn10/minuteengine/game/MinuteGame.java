package fn10.minuteengine.game;

import fn10.minuteengine.exception.FatalException;
import fn10.minuteengine.state.MinuteStateManager;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;

import static fn10.minuteengine.MinuteEngine.logger;

public abstract class MinuteGame {

    public static MinuteGame getFromInfoAndClassLoader(ClassLoader loader, MinuteGameInfo info, MinuteStateManager stateManager) {
        logger.info("Creating new instance of game {}, with game class being {}", info.name(), info.mainClass());
        try {
            Class<?> loadedClass = loader.loadClass(info.mainClass());
            MinuteGame newGame = (MinuteGame) loadedClass.getConstructor().newInstance();
            newGame.onLoad(stateManager);
            return newGame;
        } catch (ClassNotFoundException e) {
            String message = "Failed to load game class: " + info.mainClass() + " of game " + info.name();
            logger.log(Level.ERROR, message, e);
            throw new FatalException(message, -1);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            String message = "Failed to create new instance of: " + info.mainClass() + " of game " + info.name();
            logger.log(Level.ERROR, message, e);
            throw new FatalException(message, -1);
        }
    }

    /**
     * This function is called when the game loads, before it starts. You should register your states here.
     */
    public abstract void onLoad(MinuteStateManager stateManager);

    /**
     * Gets the state that this game starts with.
     * @return The state id.
     */
    public abstract Long getInitalState(MinuteStateManager stateManager);

    /**
     * This function is called when the first state starts.
     */
    public abstract void onStart();
}
