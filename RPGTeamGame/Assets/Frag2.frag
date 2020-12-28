

uniform sampler2D texture;
uniform vec2 resolution;

uniform vec4 ambientData;
int lightCount;

struct LightSource
{
    vec2 position;
    float size;
    float intensity;
    vec3 rgbData; //Red, green and blue data (color of the light)
};
uniform LightSource lights[30];

void main() {

    vec3 final;


    for (int i = 0; i < 30; i++)
    {
        if(lights[i].size == 0)
        {
            break;
        }



        // Gets the distance from the light's position and the fragment coord
        float distance = distance(gl_FragCoord.xy/resolution.xx, lights[i].position);
        // Calculates the amount of light for the fragment
        float value = 1.0 - smoothstep(-0.2, lights[i].size, distance);

        // Gets the original color from the texture
        vec4 pixel = texture2D(texture, gl_TexCoord[0].xy);

        // Applies the ambient light to the original pixel color
        vec3 ambient = pixel.rgb * ambientData.rgb * ambientData.a;

        // Calculates the light color for the pixel
        vec3 light = lights[i].rgbData.rgb * lights[i].intensity * clamp(value, 0.0, 1.0);

        // Applies the light to the pixel
        vec3 intensity = ambient + light;
        lightCount +=1;
        final += pixel.rgb * intensity;


    }
    //final = final/lightCount;
    gl_FragColor = vec4(final, 1.0);
}
