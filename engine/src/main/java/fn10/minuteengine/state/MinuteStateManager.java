package fn10.minuteengine.state;

import fn10.minuteengine.MinuteEngine;
import fn10.minuteengine.exception.StateLoadFailedException;
import fn10.minuteengine.exception.StateNotRegisteredException;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

public final class MinuteStateManager {
    private final HashMap<Long, Class<? extends State>> reg = new HashMap<>();
    private final HashMap<Long, State> loaded = new HashMap<>();
    private final MinuteEngine engine;
    public State currentState = null;

    public Long registerState(Class<? extends State> stateClass) {
        long id = Instant.now().toEpochMilli();
        reg.put(id, stateClass);
        return id;
    }

    public MinuteStateManager(MinuteEngine engine) {
        this.engine = engine;
    }


    /**
     * Gets a state from the manager. This state is loaded before it is returned, this function does not start it.
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
            state.onLoad();
            loaded.put(id, state);
            return state;
        } catch (Exception e) {
            throw new StateLoadFailedException("The state: '" + stateClas.getName() +"' failed to load.");
        }
    }

    /**
     * Change the current state in the game, loading it if it isn't already.
     * <p/>
     * <p/>
     * TODO: Add support for loading multiple states.
     * @param id The long id of the state.
     * @return The state that got started.
     */
    public State changeState(Long id) {
        State state = getState(id);
        currentState = state;
        state.onStart();
        return state;
    }
}
