package fn10.minuteengine.game;

/**
 * Information about a game that is useally serilized from json. Each game needs to have one of these at the root of the jar, called "me.game.json"
 */
public record MinuteGameInfo(String name, String mainClass) {}
