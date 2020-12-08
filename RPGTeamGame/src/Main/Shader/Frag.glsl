#version 120

varying vec2 v_vTexcoord;
varying vec4 v_vColour;

uniform sampler2D texLmap;

void main()
{
    vec4 upper = texture2D(texLmap, v_vTexcoord);
    vec4 lower = texture2D(gm_BaseTexture, v_vTexcoord);
    vec4 outColor = vec4(0.0, 0.0, 0.0, upper.a);

    if (upper.r > 0.5) {
        outColor.r = (1.0 - (1.0-lower.r) * (1.0-2.0*(upper.r-0.5)));
    } else {
        outColor.r = lower.r * (2.0*upper.r);
    }

    if (upper.g > 0.5) {
        outColor.g = (1.0 - (1.0-lower.g) * (1.0-2.0*(upper.g-0.5)));
    } else {
        outColor.g = lower.g * (2.0*upper.g);
    }

    if (upper.b > 0.5) {
        outColor.b = (1.0 - (1.0-lower.b) * (1.0-2.0*(upper.b-0.5)));
    } else {
        outColor.b = lower.b * (2.0*upper.b);
    }

    gl_FragColor = vec4(outColor.r,outColor.g,outColor.b,1.0);
}
