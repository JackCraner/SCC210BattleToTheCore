package Main.Shader;

import Main.ForeGround.Entities.Player;
import Main.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector3f;

public class Light
{
    private float size;     //as a percentage of the screen
    private float intensity;    //strength
    private Vector3f rgbData;   //colour
    private Vector2i position;
    private boolean onScreen;
    public Light()
    {
        //default Light
        this.position = new Vector2i(0,0);
        this.size = 0.3f;
        this.intensity = 2f;
        this.rgbData = new Vector3f(1f,0.8f,0.2f);

    }
    public Light(Vector2i position)
    {
        this.position = position;
        System.out.println(position);
        this.size = 0.3f;
        this.intensity = 3f;
        this.rgbData = new Vector3f(1f,0.8f,0.2f);



    }

    public Light(Player p)
    {
        //Player Light
        this.position = p.inBlock();
        this.size = 0.5f;
        this.intensity = 4f;
        this.rgbData = new Vector3f(0.3f,0.3f,0.5f);
    }
    public Light(Vector2i position, float size, float intensity, Vector3f rgbData)
    {
        this.position = position;
        this.size = size;
        this.intensity = intensity;
        this.rgbData = rgbData;
    }

    public Vector2i getPosition() {
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

    public void setPosition(Vector2i x)
    {
        position = x;
    }
    public Boolean getOnScreen(Player p)
    {
        float transform = ((Game.viewSize/Game.blockSize)/2);
        float extra = 30;
        Vector2f topLeft = new Vector2f(p.inBlock().x - transform, p.inBlock().y - transform);
        Vector2f bottomRight = new Vector2f(p.inBlock().x + transform, p.inBlock().y + transform);
        float lightPosY = 0 , lightPosX = 0;
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra))
        {
            return true;
        }
        return false;
    }

    public Vector2f convertPositionToScreen(Player p)
    {
        onScreen = false;
        float transform = ((Game.viewSize/Game.blockSize)/2);
        float extra = 30;
        Vector2f topLeft = new Vector2f(p.inBlock().x - transform, p.inBlock().y - transform);
        Vector2f bottomRight = new Vector2f(p.inBlock().x + transform, p.inBlock().y + transform);
        float lightPosY = 0 , lightPosX = 0;
        if ((position.x > topLeft.x - extra && position.x < bottomRight.x + extra) && (position.y > topLeft.y - extra && position.y < bottomRight.y + extra))
        {
            onScreen = true;
            lightPosY = ((bottomRight.y - position.y) / (bottomRight.y- topLeft.y));
            lightPosX = ((topLeft.x - position.x) / (topLeft.x - bottomRight.x));
        }
        //lightPosX = (float)Math.round(lightPosX * 10) / 10;
        //lightPosY = (float)Math.round(lightPosY * 10) / 10;

        return new Vector2f(lightPosX,lightPosY);
    }


}
