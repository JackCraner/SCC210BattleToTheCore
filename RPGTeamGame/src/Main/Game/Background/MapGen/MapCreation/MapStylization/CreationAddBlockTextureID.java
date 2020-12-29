package Main.Game.Background.MapGen.MapCreation.MapStylization;

import Main.Game.Background.MapGen.Block;
import Main.Game.Background.MapGen.Chunks.FormationChunk;
import Main.Game.Game;

public class CreationAddBlockTextureID
{
    public CreationAddBlockTextureID()
    {

    }

    public FormationChunk[][] addBlocks(FormationChunk[][] c)
    {
        for (int a = 0; a<Game.numberOfChunksX; a++)
        {
            for (int b = 0; b< Game.numberOfChunksY;b++)
            {
                Block[][] blockMapping = c[a][b].getbMapping();
                for (int a1 = 0; a1 < Game.chunkSizeBlocks; a1++)
                {
                    for (int b1 = 0; b1< Game.chunkSizeBlocks; b1++)
                    {
                        if (blockMapping[a1][b1].getID() == 1)
                        {
                            blockMapping[a1][b1].setTextureID(31);
                        }
                        else if (blockMapping[a1][b1].getID() == 0)
                        {
                            blockMapping[a1][b1].setTextureID(25);
                        }
                    }
                }

                c[a][b].setbMapping(blockMapping);
            }
        }


        return c;
    }

}
