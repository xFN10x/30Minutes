package fn10.minuteengine.audio;

import fn10.minuteengine.state.State;
import fn10.minuteengine.util.MinuteRandomUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.util.HashMap;

import static org.lwjgl.openal.ALC11.*;

public class MinuteAudioEngine {

    private final HashMap<State, Long> stateSourceIds = new HashMap<>();
    private final HashMap<Long, Source> sourceIds = new HashMap<>();

    public MinuteAudioEngine() {
        long device = alcOpenDevice((CharSequence) null);
        alcMakeContextCurrent(alcCreateContext(device, (int[]) null));
        ALCCapabilities alccap =
                ALC.createCapabilities(device);
        //ALCapabilities alcap =
        AL.createCapabilities(alccap);
        /*id = alGenBuffers();
        source = alGenSources();
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(MinuteAssetUtils.getAsset("/test/test.wav", null));
            AudioFormat format = audioInputStream.getFormat();
            ByteBuffer buf = BufferUtils.createByteBuffer(audioInputStream.available());
            buf.put(audioInputStream.readAllBytes());
            buf.flip();
            alBufferData(id, AL_FORMAT_STEREO16, buf, (int) format.getSampleRate());
            alSourcei(source, AL_BUFFER, id);
            alSourcePlay(source);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    public Source getAudioSource(State state) {
        Long id = MinuteRandomUtils.getUnqiueId(0);
        Source source = new Source();
        sourceIds.put(id, source);
        stateSourceIds.put(state, id);
        return source;
    }
}
