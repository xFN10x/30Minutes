package fn10.minuteengine.audio;

import javax.sound.sampled.AudioFormat;

import static org.lwjgl.openal.AL11.*;

public class Source {
    private final int sourceID;
    private final int bufferID;
    private Audio audio = null;

    protected Source() {
        this.sourceID = alGenSources();
        this.bufferID = alGenBuffers();
    }

    public void setAudio(Audio aud) {
        audio = aud;
    }

    public void play() {
        if (audio == null) throw new NullPointerException("No audio is set.");
        AudioFormat format = audio.getFormat();
        alBufferData(bufferID, getAlBufferFormatFromJAudioFormat(format), audio.audioDataBuffer, (int) format.getSampleRate());
        alSourcei(sourceID, AL_BUFFER, bufferID);
        alSourcePlay(sourceID);
    }

    public static int getAlBufferFormatFromJAudioFormat(AudioFormat format) {
        int sampleBitRate = format.getSampleSizeInBits();
        int channels = format.getChannels();
        if (channels > 1) {
            //more than 2 channels could just use stereo
            if (sampleBitRate >= 16) {
                return AL_FORMAT_STEREO16;
            } else {
                return AL_FORMAT_STEREO8;
            }
        } else {
            //mono
            if (sampleBitRate >= 16) {
                return AL_FORMAT_MONO16;
            } else {
                return AL_FORMAT_MONO8;
            }
        }
    }
}
