package Main.Background.MapGen.MapCreation;

import Main.Background.MapGen.Block;
import Main.Background.MapGen.Chunk;
import Main.Background.MapGen.MapCreation.CellularA.CellularAutomata;
import Main.Game;

public class CreationOutput
{
    CellularAutomata cA = new CellularAutomata(Game.chunkSizeBlocks, Game.chunkSizePixels);
    CreationAddBlocks cB = new CreationAddBlocks();
    public CreationOutput()
    {


    }
    public Chunk[][] generateOutput(Chunk[][] c)
    {
        for (int a = 0; a<Game.numberOfChunksX; a++)
        {
            for (int b = 0; b< Game.numberOfChunksY;b++)
            {
                Block[][] blockMapping = c[a][b].getChunkMapping();
                blockMapping = cB.addBlocks(blockMapping);
                c[a][b].setChunkMapping(blockMapping);
            }
        }


        return c;
    }



}
