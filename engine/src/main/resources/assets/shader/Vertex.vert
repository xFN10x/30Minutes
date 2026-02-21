//the position of the vertex as specified by our renderer
attribute vec3 Position;

void main() {
    gl_Position = vec4(Position, 1.0);
}