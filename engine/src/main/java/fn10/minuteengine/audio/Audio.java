package fn10.minuteengine.audio;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public class Audio {
    protected final ByteBuffer audioDataBuffer;

    protected Audio(InputStream stream) throws IOException {
        audioDataBuffer = BufferUtils.createByteBuffer(stream.available());
        audioDataBuffer.put(stream.readAllBytes());
    }

    public static Audio loadAsset(URL asset) throws IOException {
        return new Audio(asset.openStream());
    }
}
