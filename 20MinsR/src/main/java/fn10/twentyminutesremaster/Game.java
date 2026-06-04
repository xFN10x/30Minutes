package fn10.twentyminutesremaster;

import fn10.minuteengine.game.MinuteGame;
import fn10.minuteengine.state.MinuteStateManager;

public class Game extends MinuteGame {

    @Override
    public void onLoad(MinuteStateManager stateManager) {

    }

    @Override
    public Long getInitialState(MinuteStateManager stateManager) {
        return 0L;
    }
}
