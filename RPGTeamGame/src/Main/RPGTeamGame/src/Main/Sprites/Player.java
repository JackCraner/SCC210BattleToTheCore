package Main.Sprites;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Player extends Sprite
{
    View pView;
    float pSpeed;

    public Player(ConstTexture pTexture, View pView, float pSpeed, Vector2f pPosition)
    {
        super(pTexture);
        this.pView = pView;
        this.pSpeed = pSpeed;
        this.setPosition(pPosition);

    }


    public void movePlayer(float x, float y)
    {


        move(x *pSpeed, y* pSpeed);
        pView.move(x*pSpeed, y*pSpeed);


    }
    public Vector2f inChunk(int chunkSize)
    {
        double chunkX =this.getPosition().x/chunkSize;
        double chunkY = this.getPosition().y/chunkSize;
        //System.out.println("CHUNK " + (float)Math.floor(chunkX) + ", " + (float)Math.floor(chunkY));
        return new Vector2f((float)Math.floor(chunkX), (float)Math.floor(chunkY));
    }


}

