package fn10.thirtyminutes.music;

import fn10.minuteengine.util.MinuteAssetUtils;
import fn10.thirtyminutes.ThirtyMinutes;

import java.net.URL;

public record Soundtrack(String name, URL TitleSong, URL... RandomSongs) {
    public static final Soundtrack DEFAULT = new Soundtrack(
            "30 Minutes Soundtrack",
            asset("30_Minutes.wav"),
            asset("Warmth.wav"),
            asset("Waste_Of_Water.wav")
    );

    private static URL asset(String name) {
        return MinuteAssetUtils.getAsset("/music/" + name, ThirtyMinutes.class);
    }
}
