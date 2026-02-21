package fn10.minuteengine.exception.rendering.shaders;

import fn10.minuteengine.rendering.shaders.Shader;

public class ShaderLoadException extends RuntimeException {

    public final Class<? extends Shader> cause;
    public ShaderLoadException(String message, Class<? extends Shader> shader, Throwable e) {
        super(message, e);
        this.cause = shader;
    }
}
