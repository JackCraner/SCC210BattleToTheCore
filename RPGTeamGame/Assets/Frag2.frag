uniform sampler2D texture;
uniform vec2 resolution;

uniform vec2 time;


uniform vec4 ambientData;

uniform vec4 lightData;

uniform vec2 lightSize;

uniform vec2 position;

void main() {


    // Makes the light change its size slightly to make a fire effect
    float maxDistance = lightSize + 0.015*sin(time.x);
    // Gets the distance from the light's position and the fragment coord
    float distance = distance(gl_FragCoord.xy/resolution.xx, position);
    // Calculates the amount of light for the fragment
    float value = 1.0 - smoothstep(-0.2, maxDistance, distance);

    // Gets the original color from the texture
    vec4 pixel = texture2D(texture, gl_TexCoord[0].xy);

    // Applies the ambient light to the original pixel color
    vec3 ambient = pixel.rgb * ambientData.rgb * ambientData.a;

    // Calculates the light color for the pixel
    vec3 light = lightData.rgb * lightData.a * clamp(value, 0.0, 1.0);

    // Applies the light to the pixel
    vec3 intensity = ambient + light;
    vec3 final = pixel.rgb * intensity;

    gl_FragColor = vec4(final, 1.0);
}