package fn10.minuteengine.rendering.shaders;

import java.io.IOException;

public class SolidColourShader extends Shader {

    public SolidColourShader() throws IOException {
        super(getShaderCode(ShaderType.VERTEX, "Vertex"), getShaderCode(ShaderType.FRAGMENT, "Frag"));
    }


}
