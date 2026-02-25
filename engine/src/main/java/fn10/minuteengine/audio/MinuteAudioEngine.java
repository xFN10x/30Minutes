package fn10.minuteengine.audio;

import fn10.minuteengine.util.MinuteAssetUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static fn10.minuteengine.MinuteEngine.logger;
import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.openal.ALC11.*;

public class MinuteAudioEngine {
    private final int id;
    private final int source;

    public MinuteAudioEngine() {
        long device = alcOpenDevice((CharSequence) null);
        alcMakeContextCurrent(alcCreateContext(device, (int[]) null));
        ALCCapabilities alccap = ALC.createCapabilities(device);
        ALCapabilities alcap = AL.createCapabilities(alccap);
        id = alGenBuffers();
        source = alGenSources();
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(MinuteAssetUtils.getAsset("/test/test.wav", null));
            AudioFormat format = audioInputStream.getFormat();
            logger.info(format.getSampleSizeInBits());
            ByteBuffer buf = BufferUtils.createByteBuffer(audioInputStream.available());
            buf.put(audioInputStream.readAllBytes());
            buf.flip();
            alBufferData(id, AL_FORMAT_STEREO16, buf, (int) format.getSampleRate());
            alSourcei(source, AL_BUFFER, id);
            alSourcePlay(source);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
