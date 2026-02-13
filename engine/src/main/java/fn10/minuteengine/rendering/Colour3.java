package fn10.minuteengine.rendering;

public final class Colour3 {
    protected final float r;
    protected final float g;
    protected final float b;

    public static final Colour3 BLACK = new Colour3(0, 0, 0);
    public static final Colour3 WHITE = new Colour3(1f, 1f, 1f);

    /**
     * Creates a new colour with the float params
     * 
     * @param r red (0.0-1.0)
     * @param g green (0.0-1.0)
     * @param b blue (0.0-1.0)
     */
    public Colour3(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Creates a new colour with the int params
     * 
     * @param r red (0-255)
     * @param g green (0-255)
     * @param b blue (0-255)
     */
    public Colour3(int r, int g, int b) {

        this.r = r / 255f;
        this.g = g / 255f;
        this.b = b / 255f;
    }

    public float getRed() {
        return r;
    }

    public float getGreen() {
        return g;
    }

    public float getBlue() {
        return b;
    }
}
