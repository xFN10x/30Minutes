package fn10.thirtyminutes.music;

import fn10.minuteengine.audio.UnloadedAudio;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static fn10.minuteengine.MinuteEngine.logger;

public final class SoundtrackHandler {
    public static UnloadedAudio TITLE_SONG;
    public static ArrayList<UnloadedAudio> RANDOM_SONGS = new ArrayList<>();
    public static Soundtrack CURRENT_SOUNDTRACK;

    public static void setDefaultSoundtrack() {
        setSoundtrack(Soundtrack.DEFAULT);
    }

    public static void setSoundtrack(Soundtrack sndtrack) {
        TITLE_SONG = UnloadedAudio.ofURL(sndtrack.TitleSong());
        for (URL url : sndtrack.RandomSongs()) {
            RANDOM_SONGS.add(UnloadedAudio.ofURL(url));
        }
        CURRENT_SOUNDTRACK = sndtrack;
        logger.info("Set soundtrack to: {}", sndtrack.name());
    }

    public static void loadSoundtrack() {
        try {
            TITLE_SONG.load();
            RANDOM_SONGS.forEach(song -> {
                try {
                    song.load();
                } catch (UnsupportedAudioFileException | IOException e) {
                    logger.error("Failed to load song: " + song.getUrl(), e);
                }
            });
            logger.info("Loaded Soundtrack: {}", CURRENT_SOUNDTRACK.name());
        } catch (UnsupportedAudioFileException | IOException e) {
            logger.error("Failed to load song.", e);
        }
    }

}
