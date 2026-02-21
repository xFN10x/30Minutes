package fn10.minuteengine.rendering;

import static org.lwjgl.opengl.GL33.*;

public class Texture {
    private final int id;

    public Texture() {
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
