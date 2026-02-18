package fn10.minuteengine.rendering;

import java.util.Timer;
import java.util.TimerTask;

public class FrameRateCounter {
    private long frames = 0;
    private long frameRate;

    public long getFrameRate() {
        return frameRate;
    }

    public void frame() {
        frames++;
    }

    public FrameRateCounter() {
        //every second, it gets the frames passed, then sets it to 0
        new Timer().scheduleAtFixedRate((new TimerTask() {
            @Override
            public void run() {
                frameRate = frames;
                frames = 0;
            }
        }), 1000, 1000);
    }
}
