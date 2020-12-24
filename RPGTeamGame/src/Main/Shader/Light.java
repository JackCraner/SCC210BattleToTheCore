package Main.Shader;

import Main.ForeGround.Entities.Player;
import org.jsfml.system.Vector3f;

public class Light
{
    private float size;     //as a percentage of the screen
    private float intensity;    //strength
    private Vector3f rgbData;   //colour
    public Light()
    {
        //default Light
        this.size = 0.3f;
        this.intensity = 2f;
        this.rgbData = new Vector3f(1f,0.8f,0.2f);

    }
    public Light(Player p)
    {
        //Player Light
        this.size = 0.7f;
        this.intensity = 1f;
        this.rgbData = new Vector3f(0.3f,0.3f,0.8f);
    }
    public Light(float size, float intensity, Vector3f rgbData)
    {
        this.size = size;
        this.intensity = intensity;
        this.rgbData = rgbData;
    }

    public float getSize()
    {
        return size;
    }
    public float getIntensity()
    {
        return intensity;
    }
    public Vector3f getRgbData()
    {
        return rgbData;
    }

}
