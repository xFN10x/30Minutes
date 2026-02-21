package fn10.minuteengine.rendering.shaders;

import fn10.minuteengine.exception.rendering.shaders.ShaderLoadException;
import fn10.minuteengine.util.MinuteAssetUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static fn10.minuteengine.MinuteEngine.logger;
import static org.lwjgl.opengl.GL20.*;

public abstract class Shader implements AutoCloseable {
    protected final String vertexShaderData;
    protected final String fragShaderData;
    protected int shaderID = -1;
    protected int vertShaderID = -1;
    protected int fragShaderID = -1;
    private static Map<Class<? extends Shader>, Shader> compiledShaders = new HashMap<>();

    public static Shader getShader(Class<? extends Shader> shaderType) {
        if (compiledShaders.containsKey(shaderType)) {
            return compiledShaders.get(shaderType);
        } else {
            try {
                return shaderType.getConstructor(null).newInstance(null);
            } catch (Exception e) {
                throw new ShaderLoadException("Failed to construct shader.", shaderType, e);
            }
        }
    }

    protected Shader(String vertexShaderData, String fragShaderData) {
        this.vertexShaderData = vertexShaderData;
        this.fragShaderData = fragShaderData;
        createProgram();
    }

    protected static String getShaderCode(ShaderType type, String name) throws IOException {
        return MinuteAssetUtils.readAssetFullString("/shader/" + name + (type == ShaderType.VERTEX ? ".vs" : ".fs"), null);
    }

    public static void closeAllShaders() {
        compiledShaders.forEach((k,v) -> {
            v.close();
        });
        compiledShaders.clear();
    }

    public void use() {
        glUseProgram(shaderID);
    }

    public final int getProgramID() {
        return shaderID;
    }

    protected final void createProgram() {
        logger.info("Creating shaders...");
        vertShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShaderID, vertexShaderData);

        fragShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShaderID, fragShaderData);

        logger.info("Compiling shaders...");
        glCompileShader(vertShaderID);
        glCompileShader(fragShaderID);

        shaderID = glCreateProgram();

        glAttachShader(shaderID, vertShaderID);
        glAttachShader(shaderID, fragShaderID);

        glLinkProgram(shaderID);

        logger.info(glGetShaderInfoLog(vertShaderID));
        logger.info(glGetShaderInfoLog(fragShaderID));

        glDeleteShader(vertShaderID);
        glDeleteShader(fragShaderID);
        logger.info("Done!");

        compiledShaders.put(getClass(), this);
    }

    @Override
    public void close() {
        glDeleteProgram(shaderID);
    }

    public enum ShaderType {
        VERTEX,
        FRAGMENT
    }
}
