package fn10.minuteengine;

import fn10.minuteengine.rendering.MinuteRenderer;

public class MinuteEngine {

    public MinuteRenderer renderer;

    public Thread renderThread = new Thread(() -> {
        renderer.createWindow();
        renderer.renderLoop();
    }, "ME-Render");

    protected boolean running = false;

    public void start() {
        renderer = MinuteRenderer.initRenderer();
        running = true;
        mainLoop();
    }

    /**
     * Starts all the loops in the engine, like rendering.
     */
    private void mainLoop() {
        renderThread.start();
    }
    
    public static void main(String[] args) {
        
    }
}
