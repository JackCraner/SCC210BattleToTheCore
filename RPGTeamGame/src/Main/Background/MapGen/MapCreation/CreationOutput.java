package Main.Background.MapGen.MapCreation;

import Main.Background.MapGen.Block;
import Main.Background.MapGen.Chunks.FormationChunk;
import Main.Background.MapGen.MapCreation.MapStylization.CreationAddBlocks;
import Main.Game;

public class CreationOutput
{

    CreationAddBlocks cB = new CreationAddBlocks();
    public CreationOutput()
    {


    }
    public FormationChunk[][] generateOutput(FormationChunk[][] c)
    {
        for (int a = 0; a<Game.numberOfChunksX; a++)
        {
            for (int b = 0; b< Game.numberOfChunksY;b++)
            {
                Block[][] blockMapping = c[a][b].getbMapping();
                blockMapping = cB.addBlocks(blockMapping);
                c[a][b].setbMapping(blockMapping);
            }
        }


        return c;
    }



}
