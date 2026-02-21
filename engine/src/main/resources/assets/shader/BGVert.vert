layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord; // Input texture coordinates

out vec2 TexCoord; // Output to fragment shader

void main()
{
    gl_Position = vec4(aPos, 1.0);
    TexCoord = aTexCoord; // Pass coordinates to fragment shader
}
