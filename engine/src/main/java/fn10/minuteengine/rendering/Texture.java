package fn10.minuteengine.rendering;

import fn10.minuteengine.rendering.renderables.Text;
import fn10.minuteengine.util.MinuteAssetUtils;
import fn10.minuteengine.util.MinuteVectorIntUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import javax.imageio.ImageIO;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.memAlloc;

public class Texture {
    private Vector2ic size;
    private final ByteBuffer data;
    private final int GLTexture;
    private static final HashMap<Long, Texture> cached = new HashMap<>();

    private Texture(Vector2ic size, BufferedImage bi) {
        this.size = size;

        //JOptionPane.showMessageDialog(null, bi.getType());

        int[] pixels = bi.getData().getPixels(0, 0, size.x(), size.y(), (int[]) null);
        ArrayList<Byte> bytes = new ArrayList<>();
        for (int pixel : pixels) {
            bytes.add((byte) pixel);
        }

        data = memAlloc(4 * size.x() * size.y());
        data.put(ArrayUtils.toPrimitive(bytes.toArray(new Byte[0])));
        data.flip();
        GLTexture = glGenTextures();
        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size.x(), size.y(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, GLTexture);
    }

    public Vector2ic getSize() {
        return size;
    }

    public ByteBuffer getData() {
        return data;
    }

    public void setSize(Vector2ic vec) {
        this.size = vec;
    }

    private static Texture test = null;

    static {

    }

    public static Texture ofTest() {
        if (test == null) {
            BufferedImage bi = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            BufferedImage out = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            try {
                bi.createGraphics().drawImage(ImageIO.read(MinuteAssetUtils.getAsset("/test/img.png", null)), 0, 0, null);
                //https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.concatenate(AffineTransform.getScaleInstance(1, -1));
                affineTransform.concatenate(AffineTransform.getTranslateInstance(0, -64));
                AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                affineTransformOp.filter(bi, out);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            test = new Texture(new Vector2i(64, 64), out);
            return test;
        }
        else
            return test;
    }
    
    /**
     * Creates, or gets a cached texture 
     * @param id The ID of the texture
     * @param bi The texture as a BufferedImage
     * @return A new texture if an instance of it with the suppliled ID doesn't exist, or 
     */
    public static Texture ofBufferedImage(Long id, BufferedImage bi) {
        if (cached.containsKey(id)) {
            return cached.get(id);
        }
        else 
        {
            cached.put(id, new Texture(MinuteVectorIntUtils.getBufferedImageSize(bi), bi));
            return cached.get(id);
        }
    }

    public static void clearCache() {cached.clear();}
    public static void clearCache(Long id) {cached.remove(id);}
}
