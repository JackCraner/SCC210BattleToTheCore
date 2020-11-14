package Main.MapGen;

import Main.MapGen.CellularAutomata.Cave;
import org.jsfml.system.Vector2f;

public abstract class Generator
{
    int chunkSizeBlocks;
    int ChunkSizePixels;

    int[][] chunkBinaryMapping;

    Vector2f cPosition;
    public Generator(int chunkSizeBlocks, int chunkSizePixels, Vector2f cPosition)
    {
        this.chunkSizeBlocks = chunkSizeBlocks;
        this.ChunkSizePixels = chunkSizePixels;
        this.cPosition = cPosition;
        chunkBinaryMapping = new int[chunkSizeBlocks][chunkSizeBlocks];
    }

    public void setBinaryMapping(int[][] s)
    {
        chunkBinaryMapping = s;
    }
    public int[][] getChunkBinaryMapping()
    {
        return chunkBinaryMapping;
    }
    public int getChunkSizeBlocks()
    {
        return chunkSizeBlocks;
    }
    public int getChunkSizePixels()
    {
        return ChunkSizePixels;
    }


    public abstract int[][] generateBinaryMapping(int[][] inputMap);





}
