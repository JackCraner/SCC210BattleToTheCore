package Main.ForeGround.Entities;

import Main.DataTypes.PositionVector;
import Main.ForeGround.Interfaces.Illuminator;
import Main.Game;
import Main.Shader.Light;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Player extends MEntity implements Illuminator
{
    View playerCamera;
    PositionVector pos;
    Light l;
    public Player(int ID, Vector2f position, int viewSize)
    {
        super(ID, position);
        setPosition(position);
        setTexture(getEntityTexture());
        playerCamera = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        playerCamera.setCenter(this.getPosition());
        l = new Light(this);
    }

    public Vector2f inChunk()
    {
        double chunkX =this.getPosition().x/ Game.chunkSizePixels;
        double chunkY = this.getPosition().y/Game.chunkSizePixels;
        return new Vector2f((float)Math.floor(chunkX), (float)Math.floor(chunkY));
    }


    public Vector2i inBlock()
    {
        return new Vector2i((int)(this.getPosition().x/Game.blockSize), (int)(this.getPosition().y/Game.blockSize));
    }

    public View getpView()
    {
        return playerCamera;
    }
    public void zoom(int x)
    {
        float newSize = playerCamera.getSize().x + x;
        playerCamera.setSize(new Vector2f(newSize,newSize));
    }
    public void moveEntity()
    {
        playerCamera.setCenter(this.getPosition());
        l.setPosition(inBlock());
    }


    public Light getLight() {
        return l;
    }
}
