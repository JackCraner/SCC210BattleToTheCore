package Main.Background.MapGen;

import Main.Physics.Collidable;
import org.jsfml.system.Vector2f;

public class Block
{
    private int blockID, xPosition, yPosition;
    public Block(int blockID, int x, int y)
    {
        this.blockID = blockID;
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

    public Vector2f getPosition() {
        return new Vector2f(xPosition, yPosition);
    }
}
