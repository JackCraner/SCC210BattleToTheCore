package Main.MapGen.PerlinNoise;
import java.util.Random;
import Main.MapGen.CellularAutomata.Cave;
import Main.MapGen.Generator;
import org.jsfml.system.Vector2f;

public class PerlinNoise extends Generator
{
    public PerlinNoise(int chunkSizeBlocks, int chunkSizePixels, Vector2f chunkPosition)
    {
        super(chunkSizeBlocks,chunkSizePixels, chunkPosition);
        int[][] binaryMapping = getChunkBinaryMapping();

        setBinaryMapping(generateBinaryMapping(binaryMapping));
    }

    @Override
    public int[][] generateBinaryMapping(int[][] inputMap)
    {
        return new int[0][];
    }
    public Cave getCave()
    {
        return null;
    }
}
