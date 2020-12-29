package Main.Game.DataTypes;

import Main.Game.Game;
import org.jsfml.system.Vector2f;

public class PositionVector
{
    public final float x;
    public final float y;
    public PositionVector(float v, float v1)
    {
        this.x = v;
        this.y = v1;
    }
    public PositionVector(Vector2f v)
    {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2f getVector()
    {
        return new Vector2f(x,y);
    }
    public Vector2f inBlock()
    {
        Vector2f chunkPos = inChunk();
        return new Vector2f((float)Math.floor((x - chunkPos.x * Game.CHUNKSIZEPIXELS)/Game.CHUNKSIZEBLOCKS), (float)Math.floor((y - chunkPos.y * Game.CHUNKSIZEPIXELS)/Game.CHUNKSIZEBLOCKS));
    }
    public Vector2f inChunk()
    {
        double chunkX =x/ Game.CHUNKSIZEPIXELS;
        double chunkY = y/Game.CHUNKSIZEPIXELS;
        //System.out.println("CHUNK " + (float)Math.floor(chunkX) + ", " + (float)Math.floor(chunkY));
        return new Vector2f((float)Math.floor(chunkX), (float)Math.floor(chunkY));
    }
}
