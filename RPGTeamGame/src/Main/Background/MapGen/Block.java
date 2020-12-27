package Main.Background.MapGen;

import Main.Game;
import Main.Physics.Collidable;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Block
{
    private int blockID, textureID, xPosition, yPosition;
    public Block(int blockID, int x, int y)
    {
        this.blockID = blockID;
        xPosition = x;
        yPosition = y;
    }
    public Block(int blockID, int textureID, int x, int y)
    {
        this.blockID = blockID;
        this.textureID = textureID;
        xPosition = x;
        yPosition = y;
    }

    public int getID()
    {
        return blockID;
    }
    public void setID(int x)
    {
        blockID = x;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public Vector2f getPosition() {
        return new Vector2f(xPosition, yPosition);
    }



    public Vector2i inBlock()
    {
        return new Vector2i((int)(this.getPosition().x/ Game.blockSize), (int)(this.getPosition().y/Game.blockSize));
    }

}
