package Main.ForeGround.Entities;

import Main.DataTypes.PositionVector;
import Main.Game;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Player extends MovingEntity
{
    View playerCamera;
    PositionVector pos;
    public Player(int ID, Vector2f position, int viewSize)
    {
        super(ID, position);
        setPosition(position);
        setTexture(getEntityTexture());
        playerCamera = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        playerCamera.setCenter(this.getPosition());
    }

    public Vector2f inChunk()
    {
        double chunkX =this.getPosition().x/ Game.chunkSizePixels;
        double chunkY = this.getPosition().y/Game.chunkSizePixels;
        return new Vector2f((float)Math.floor(chunkX), (float)Math.floor(chunkY));
    }

    public Vector2f inBlock()
    {
        Vector2f chunkPos = inChunk();
        Vector2f charPos = this.getPosition();
        //System.out.println(blockSize);
        return new Vector2f((float)Math.floor((charPos.x - chunkPos.x * Game.chunkSizePixels)/Game.blockSize), (float)Math.floor((charPos.y - chunkPos.y * Game.chunkSizePixels)/Game.blockSize));
    }

    public View getpView()
    {
        return playerCamera;
    }

    public void moveEntity()
    {
        playerCamera.setCenter(this.getPosition());
    }

}
