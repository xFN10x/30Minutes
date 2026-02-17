package fn10.minuteengine.util;

import fn10.minuteengine.MinuteEngine;
import fn10.minuteengine.game.MinuteGame;
import org.apache.logging.log4j.Level;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static fn10.minuteengine.MinuteEngine.logger;

public class MinuteAssetUtils {
    /**
     * Get an asset from the engine, or a game.
     *
     * @param path The path inside the assets folder to get. (e.g. {@code /font/opensans.ttf}) Set this to {@code null} to use internal assets.
     * @return a URL of the resource.
     */
    public static URL getAsset(String path, @Nullable MinuteGame game) {
        if (game != null)
            return game.getClass().getResource("/assets" + path);
        else
            return MinuteEngine.class.getResource("/assets" + path);
    }

    /**
     * Get an asset as a stream from the engine, or a game.
     *
     * @param path The path inside the assets folder to get. (e.g. {@code /font/opensans.ttf}) Set this to {@code null} to use internal assets.
     * @return a InputStream of the resource.
     */
    @Nullable
    public static InputStream getStreamAsset(String path, @Nullable MinuteGame game) {
        try {
            return  getAsset(path, game).openStream();
        } catch (IOException e) {
            logger.log(Level.ERROR, "Failed to get asset as stream");
            return null;
        }
    }
}
