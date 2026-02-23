package fn10.minuteengine.rendering.shaders;

import java.io.IOException;

public class TextureShader extends Shader {

    public TextureShader() throws IOException {
        super(getShaderCode(ShaderType.VERTEX, "Vertex"), getShaderCode(ShaderType.FRAGMENT, "TextureFrag"));
    }


}
