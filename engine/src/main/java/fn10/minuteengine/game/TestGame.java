package fn10.minuteengine.game;

import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;

public class TestGame extends MinuteGame {
    @Override
    public Long getInitalState(MinuteStateManager stateManager) {
        return null;
    }

    @Override
    public void onLoad(MinuteStateManager stateManager) {
        stateManager.registerState(TestState.class);
    }

    @Override
    public void onStart() {

    }
}
