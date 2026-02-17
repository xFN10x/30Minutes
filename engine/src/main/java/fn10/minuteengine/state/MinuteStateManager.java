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

    public Long registerState(Class<? extends State> stateClass) {
        long id = Instant.now().toEpochMilli();
        reg.put(id, stateClass);
        return id;
    }

    public MinuteStateManager(MinuteEngine engine) {
        this.engine = engine;
    }


    public State getState(Long id) {
        if (loaded.containsKey(id)) {
            State state = loaded.get(id);
            state.onStart();
            return state;
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
}
