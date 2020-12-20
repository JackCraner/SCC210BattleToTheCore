package Main.Background.MapGen.MapCreation;

import Main.Background.MapGen.Block;
import Main.Background.MapGen.MapCreation.CellularA.CellularAutomata;
import Main.Game;

public class CreationAddBlocks
{
    public CreationAddBlocks()
    {

    }

    public Block[][] addBlocks(Block[][] oBlocks)
    {
        Block[][] newBlock = oBlocks;
        for (int a = 0; a < Game.chunkSizeBlocks; a++)
        {
            for (int b = 0; b< Game.chunkSizeBlocks; b++)
            {
                if (newBlock[a][b].getID() == 1)
                {
                    newBlock[a][b].setID(31);
                }
                else if (newBlock[a][b].getID() == 0)
                {
                    newBlock[a][b].setID(25);
                }
            }
        }

        return newBlock;
    }

}
