package Main.Sprites;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.Sprite;

public class Block extends Sprite
{

    private int blockID;

    public Block(int blockID, ConstTexture texture)
    {
        super(texture);
        this.blockID = blockID;


    }

    public int getID()
    {
        return blockID;
    }



}
