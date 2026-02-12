package fn10.minuteengine.rendering.renderables;

import fn10.minuteengine.rendering.Colour3;
import fn10.minuteengine.rendering.Tri3;

public class Triangle extends Renderable {

    @Override
    public Tri3[] getTriangleList() {
        return new Tri3[] { new Tri3(Colour3.WHITE, new float[] { 
            0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f }) };
    }

}
