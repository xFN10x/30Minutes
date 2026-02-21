uniform sampler2D tex;
uniform highp vec2 speed;
uniform highp vec2 tiles;

uniform highp vec2 res;

#ifdef GL_ES
precision highp float;
#endif

void main() {
    vec2 tiles = (tiles*(res.y/res.x));
    
    vec2 tileUV = fract(((v_texCoord*tiles)+(u_timer*speed)));
    
    gl_FragColor = texture(tex,tileUV);
    
}