package fn10.thirtyminutes;

import fn10.minuteengine.game.MinuteGame;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;
import fn10.thirtyminutes.state.GameLoadState;

public class ThirtyMinutes extends MinuteGame {

    @Override
    public Long getInitalState(MinuteStateManager stateManager) {
        return stateManager.registerState(GameLoadState.class);
    }

    @Override
    public void onLoad(MinuteStateManager stateManager) {

    }

    @Override
    public void onStart() {

    }
}
