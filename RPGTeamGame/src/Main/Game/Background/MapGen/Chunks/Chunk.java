package Main.Game.Background.MapGen.Chunks;

import Main.Game.Background.MapGen.Block;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Chunk
{
    Block[][] bMapping;
    Vector2f position;


    public Chunk(Block[][] bMapping)
    {
        this.bMapping = bMapping;
        position = new Vector2f(0,0);
    }
    public Chunk(Block[][] bMapping, Vector2f position)
    {
        this.bMapping = bMapping;
        this.position = position;
    }
    public Chunk(Block[][] bMapping, Vector2i position)
    {
        this.bMapping = bMapping;
        this.position = new Vector2f(position.x, position.y);
    }

    public Vector2f getPosition() {
        return position;
    }

    public Block[][] getbMapping() {
        return bMapping;
    }

    public void setbMapping(Block[][] bMapping) {
        this.bMapping = bMapping;
    }

    public Block getBlockAt(Vector2i pos)
    {
        return bMapping[pos.x][pos.y];
    }
    public Block getBlockAt(int a, int b)
    {
        return bMapping[a][b];
    }
}
