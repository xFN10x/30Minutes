package fn10.minuteengine.audio;

import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static fn10.minuteengine.MinuteEngine.logger;

public class Audio {
    protected final ByteBuffer audioDataBuffer;
    private final AudioFormat format;

    protected Audio(InputStream stream, AudioFormat format) throws IOException {
        audioDataBuffer = BufferUtils.createByteBuffer(stream.available());
        audioDataBuffer.put(stream.readAllBytes());
        audioDataBuffer.flip();
        this.format = format;
    }

    public static Audio loadAsset(URL asset) throws IOException, UnsupportedAudioFileException {
        logger.info("Loading asset: {}", asset.getPath());
        AudioInputStream stream = AudioSystem.getAudioInputStream(asset);
        return new Audio(stream, stream.getFormat());
    }

    public AudioFormat getFormat() {
        return format;
    }
}
