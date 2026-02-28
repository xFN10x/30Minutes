package fn10.minuteengine.audio;

import fn10.minuteengine.game.MinuteGame;
import fn10.minuteengine.util.MinuteAssetUtils;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import static fn10.minuteengine.MinuteEngine.logger;

/**
 * An UnloadedAudio is basiclly an Audio object that doesn't have to be loaded.
 */
public class UnloadedAudio {
    private final URL url;
    private Audio loaded = null;

    private UnloadedAudio(URL url) {
        this.url = url;
    }

    public static UnloadedAudio ofAsset(String path, Class<? extends MinuteGame> game) {
        return new UnloadedAudio(MinuteAssetUtils.getAsset(path,game));
    }
    public static UnloadedAudio ofURL(URL url) {
        return new UnloadedAudio(url);
    }

    public Audio load() throws UnsupportedAudioFileException, IOException {
        Audio audio = Audio.loadAsset(url);
        loaded = audio;
        return audio;
    }

    public URL getUrl() {
        return url;
    }

    public Audio getAudio() {
        if (loaded == null) {
            try {
                return load();
            } catch (Exception e) {
                logger.error("Failed to load audio.", e);
                return null;
            }
        } else return loaded;
    }
}
