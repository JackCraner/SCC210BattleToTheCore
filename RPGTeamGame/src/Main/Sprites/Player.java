package Main.Sprites;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Player extends Sprite
{
    View pView;
    View pMiniMap;
    Text fpsC;
    float pSpeed;

    public Player(ConstTexture pTexture, View pView, View pMiniMap, Text fpsC, float pSpeed, Vector2f pPosition)
    {
        super(pTexture);
        this.pSpeed = pSpeed;
        this.setPosition(pPosition);
        this.pView = pView;
        pView.setCenter(pPosition);
        this.fpsC = fpsC;
        this.pMiniMap = pMiniMap;
        fpsC.setPosition(new Vector2f((pPosition.x + (pView.getSize().x)/2) - fpsC.getLocalBounds().width*2,0));
        pMiniMap.setCenter(new Vector2f((pPosition.x + (pView.getSize().x)/2) - fpsC.getLocalBounds().width*2,0));
    }


    public void movePlayer(float x, float y)
    {
        move(x *pSpeed, y* pSpeed);
        pView.move(x*pSpeed, y*pSpeed);
        fpsC.move(x*pSpeed, y*pSpeed);


    }
    public Vector2f inChunk(int chunkSize)
    {
        double chunkX =this.getPosition().x/chunkSize;
        double chunkY = this.getPosition().y/chunkSize;
        //System.out.println("CHUNK " + (float)Math.floor(chunkX) + ", " + (float)Math.floor(chunkY));
        return new Vector2f((float)Math.floor(chunkX), (float)Math.floor(chunkY));
    }
    public Vector2f inBlock(int chunkSize, int blockSize)
    {
        Vector2f chunkPos = inChunk(chunkSize);
        Vector2f charPos = this.getPosition();
        return new Vector2f((float)Math.floor((charPos.x - chunkPos.x * chunkSize)/blockSize), (float)Math.floor((charPos.y - chunkPos.y * chunkSize)/blockSize));
    }


}