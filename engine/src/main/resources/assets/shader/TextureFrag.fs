#version 330 core
out vec4 FragColor;
  
in vec4 vertexColor; // the input variable from the vertex shader (same name and same type)  
in vec2 uv;
uniform sampler2D tex;

void main()
{
    FragColor = texture(tex, uv);
} 