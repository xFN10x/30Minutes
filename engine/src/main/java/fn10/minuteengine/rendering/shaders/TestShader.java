package fn10.minuteengine.rendering.shaders;

import java.io.IOException;

public class TestShader extends Shader {

    public TestShader() throws IOException {
        super(getShaderCode(ShaderType.VERTEX, "Vertex"), getShaderCode(ShaderType.FRAGMENT, "Frag"));
    }


}
