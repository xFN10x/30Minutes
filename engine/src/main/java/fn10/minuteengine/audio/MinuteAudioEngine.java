package fn10.minuteengine.audio;

import fn10.minuteengine.MinuteEngine;
import fn10.minuteengine.state.State;
import fn10.minuteengine.util.MinuteRandomUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.util.HashMap;

import static org.lwjgl.openal.ALC11.*;

public class MinuteAudioEngine {

    private final HashMap<State, Long> stateSourceIds = new HashMap<>();
    private final HashMap<Long, Source> sourceIds = new HashMap<>();

    public MinuteAudioEngine() {
        try {
            long device = alcOpenDevice((CharSequence) null);
            alcMakeContextCurrent(alcCreateContext(device, (int[]) null));
            ALCCapabilities alccap =
                    ALC.createCapabilities(device);
            //ALCapabilities alcap =
            AL.createCapabilities(alccap);
        } catch (Exception e) {
            MinuteEngine.logger.log(Level.ERROR, "Failed to create audio engine.", e);
        }
    }

    public Source newAudioSource(State state) {
        Long id = MinuteRandomUtils.getUnqiueId(0);
        Source source = new Source();
        sourceIds.put(id, source);
        stateSourceIds.put(state, id);
        return source;
    }
}
