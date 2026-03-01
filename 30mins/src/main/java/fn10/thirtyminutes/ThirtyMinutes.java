package fn10.thirtyminutes;

import fn10.minuteengine.game.MinuteGame;
import fn10.minuteengine.state.MinuteStateManager;
import fn10.minuteengine.state.State;
import fn10.minuteengine.state.TestState;
import fn10.thirtyminutes.state.GameLoadState;
import fn10.thirtyminutes.state.TitleState;

public class ThirtyMinutes extends MinuteGame {

    public static Long GAME_LOAD_STATE;
    public static Long TITLE_STATE;

    @Override
    public Long getInitialState(MinuteStateManager stateManager) {
        return GAME_LOAD_STATE;
    }

    @Override
    public void onLoad(MinuteStateManager stateManager) {
        GAME_LOAD_STATE = stateManager.registerState(GameLoadState.class);
        TITLE_STATE = stateManager.registerState(TitleState.class);
    }
}
