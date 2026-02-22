#version 330 core
layout (location = 0) in vec3 p_pos;
layout (location = 1) in vec3 p_colour;
layout (location = 2) in vec2 p_uv;

out vec4 vertexColor;
out vec2 uv;

uniform float me_Time;

void main()
{
    gl_Position = vec4(p_pos, 1) ; // see how we directly give a vec3 to vec4's constructor
    vertexColor = vec4(p_colour * (sin(me_Time * 5) + 1.2 ) , 1.0); // set the output variable to a dark-red color
    uv = p_uv;
}