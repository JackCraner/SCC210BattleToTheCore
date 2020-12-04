package Main.ForeGround.Entities;

import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Player extends MEntity
{
    View playerCamera;
    public Player(int ID, Vector2f position, int viewSize)
    {
        super(ID, position);
        playerCamera = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        playerCamera.setCenter(this.getPosition());
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

    public View getpView()
    {
        return playerCamera;
    }

    @Override
    public void moveEntity()
    {
        move(getVelocity());
        friction();
        playerCamera.setCenter(this.getPosition());
    }

}
