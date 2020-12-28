package Main.Background.MapGen.MapCreation.MapStylization;

import Main.Background.MapGen.Chunks.Chunk;
import Main.ForeGround.Entities.Chest;
import Main.ForeGround.Entities.Torch;
import Main.Game;

import java.util.ArrayList;

public class CreationOfChests
{
    Chunk map;
    ArrayList<Chest> chestList = new ArrayList<>();
    public CreationOfChests(Chunk map)
    {
        this.map = map;
        createChests();
    }

    public void createChests()
    {
        int mapSizeBlocksX = Game.numberOfChunksX * Game.chunkSizeBlocks;
        int mapSizeBlocksY = Game.numberOfChunksY * Game.chunkSizeBlocks;
        for (int a = 0; a<mapSizeBlocksX;a++)
        {
            for (int b = 0; b < mapSizeBlocksY - 1; b++)
            {
                if (map.getBlockAt(a,b).getID() ==1 && map.getBlockAt(a,b+1).getID() == 0)
                {
                    if ((Math.random() < 0.02))
                    {

                        Chest c = new Chest(map.getBlockAt(a, b).getPosition());
                        chestList.add(c);
                    }
                }

            }
        }
    }

    public ArrayList<Chest> getChestList() {
        return chestList;
    }
}
