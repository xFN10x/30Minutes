package fn10.minuteengine.util;

import fn10.minuteengine.game.MinuteGame;

import java.net.URL;

public class MinuteAssetUtils {
    /**
     * Get an asset from the engine, or a game.
     *
     * @param path The path inside the assets folder to get. (e.g. {@code /font/opensans.ttf}
     * @return a URL of the resource.
     */
    public static URL getAsset(String path, MinuteGame game) {
        return game.getClass().getResource("/assets" + path);
    }
}
