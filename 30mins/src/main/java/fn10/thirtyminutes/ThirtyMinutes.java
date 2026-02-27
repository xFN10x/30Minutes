package fn10.thirtyminutes;

import fn10.minuteengine.game.MinuteGame;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;

public class ThirtyMinutes extends MinuteGame {

    @Override
    public Long getInitalState(MinuteStateManager stateManager) {
        return stateManager.registerState(TestState.class);
    }

    @Override
    public void onLoad(MinuteStateManager stateManager) {

    }

    @Override
    public void onStart() {

    }
}
