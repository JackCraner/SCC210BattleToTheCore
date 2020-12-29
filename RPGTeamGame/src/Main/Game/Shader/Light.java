package Main.Game.Shader;

import Main.Game.Player.Player;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector3f;
//the exact position of the light should be the exact position of the sprite as the position.
public class Light
{
    private float size;     //as a percentage of the screen
    private float intensity;    //strength
    private Vector3f rgbData;   //colour
    private Vector2f position;
    private boolean onScreen;
    public Light()
    {
        //default Light
        this.position = new Vector2f(0,0);
        this.size = 0.3f;
        this.intensity = 2f;
        this.rgbData = new Vector3f(1f,0.8f,0.2f);

    }
    public Light(Vector2f position)
    {
        this.position = position;
        this.size = 0.3f;
        this.intensity = 3f;
        this.rgbData = new Vector3f(1f,0.8f,0.2f);



    }

    public Light(Player p)
    {
        //Player Light
        this.position = p.getPosition();
        this.size = 0.5f;
        //playing with size = 0.3f is fun
        this.intensity = 4f;
        this.rgbData = new Vector3f(0.3f,0.3f,0.5f);
    }
    public Light(Vector2f position, float size, float intensity, Vector3f rgbData)
    {
        this.position = position;
        this.size = size;
        this.intensity = intensity;
        this.rgbData = rgbData;
    }

    public Vector2f getPosition() {
        return position;
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

    public void setPosition(Vector2f x)
    {
        position = x;
    }
    public Boolean getOnScreen(Player p)
    {
        float transform = ((Game.VIEWSIZE /2));
        float extra = 250;
        Vector2f topLeft = new Vector2f(p.getPosition().x - transform, p.getPosition().y - transform);
        Vector2f bottomRight = new Vector2f(p.getPosition().x + transform, p.getPosition().y + transform);
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra))
        {
            return true;
        }
        return false;
    }

    public Vector2f convertPositionToScreen(Player p)
    {

        float transform = ((Game.VIEWSIZE /2));
        float extra = 250;
        Vector2f topLeft = new Vector2f(p.getPosition().x - transform, p.getPosition().y - transform);
        Vector2f bottomRight = new Vector2f(p.getPosition().x + transform, p.getPosition().y + transform);
        float lightPosY = 0 , lightPosX = 0;
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra))
        {
            lightPosY = ((bottomRight.y - position.y) / (bottomRight.y- topLeft.y));
            lightPosX = ((topLeft.x - position.x) / (topLeft.x - bottomRight.x));
        }
        //lightPosX = (float)Math.round(lightPosX * 10) / 10;
        //lightPosY = (float)Math.round(lightPosY * 10) / 10;

        return new Vector2f(lightPosX,lightPosY);
    }


}
