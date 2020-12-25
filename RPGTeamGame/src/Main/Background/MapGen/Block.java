package Main.Background.MapGen;

import Main.Game;
import Main.Physics.Collidable;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Block
{
    private int blockID, xPosition, yPosition;
    private Boolean collidable;
    public Block(int blockID, int x, int y)
    {
        this.blockID = blockID;
        xPosition = x;
        yPosition = y;
        collidable = false;
    }
    public Block(int blockID, int x, int y, Boolean collidable)
    {
        this.blockID = blockID;
        xPosition = x;
        yPosition = y;
        this.collidable = collidable;
    }

    public int getID()
    {
        return blockID;
    }
    public void setID(int x)
    {
        blockID = x;
    }

    public Boolean getCollidable() {
        return collidable;
    }

    public Vector2i getPosition() {
        return new Vector2i(xPosition, yPosition);
    }

    public Vector2i inBlock()
    {
        return new Vector2i((int)(this.getPosition().x/ Game.blockSize), (int)(this.getPosition().y/Game.blockSize));
    }

}
