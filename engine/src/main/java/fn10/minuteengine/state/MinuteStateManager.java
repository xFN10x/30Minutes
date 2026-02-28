package fn10.minuteengine.state;

import fn10.minuteengine.MinuteEngine;
import fn10.minuteengine.audio.MinuteAudioEngine;
import fn10.minuteengine.exception.state.StateLoadFailedException;
import fn10.minuteengine.exception.state.StateNotRegisteredException;
import fn10.minuteengine.util.MinuteRandomUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.time.Instant;
import java.util.HashMap;

import static fn10.minuteengine.MinuteEngine.logger;

public final class MinuteStateManager {
    private final HashMap<Long, Class<? extends State>> reg = new HashMap<>();
    private final HashMap<Long, State> loaded = new HashMap<>();
    private final MinuteEngine engine;
    @NotNull
    public State currentState = new BlankState();
    public HashMap<State, Thread> stateThreads = new HashMap<>();

    public Long registerState(Class<? extends State> stateClass) {
        long id = MinuteRandomUtils.getUnqiueId(0);
        reg.put(id, stateClass);
        return id;
    }

    public MinuteStateManager(MinuteEngine engine) {
        this.engine = engine;
    }


    /**
     * Gets a state from the manager. This state is loaded before it is returned, this function does not start it.
     *
     * @param id The long id of the state
     * @return The state after {@link State#onLoad()} is called.
     * @see MinuteStateManager#changeState(Long)
     */
    public State getState(Long id) {
        if (loaded.containsKey(id)) {
            return loaded.get(id);
        }
        if (!reg.containsKey(id)) throw new StateNotRegisteredException("The state with ID: " + id + ", is not found.");
        Class<? extends State> stateClas = reg.get(id);
        try {
            State state = stateClas.getConstructor().newInstance();

            state.audioEngine = engine.audioEngine;
            state.engine = engine;

            state.onLoad();
            loaded.put(id, state);
            return state;
        } catch (Exception e) {
            throw new StateLoadFailedException("The state: '" + stateClas.getName() + "' failed to load.", e);
        }
    }

    /**
     * Change the current state in the game, loading it if it isn't already.
     * <p/>
     * <p/>
     * TODO: Add support for loading multiple states.
     *
     * @param id The long id of the state.
     * @return The state that got started.
     */
    public State changeState(Long id) {
        logger.info(Thread.currentThread().getName());
        State state = getState(id);
        currentState = state;
        currentState.onStart();
        return state;
    }
}
