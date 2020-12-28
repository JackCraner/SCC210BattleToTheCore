package Main.Background.MapGen.MapCreation.MapStylization;

import Main.Background.MapGen.Chunks.Chunk;
import Main.ForeGround.Entities.Torch;
import Main.Game;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class CreationAddTorches extends CreationCalculator
{
    Chunk map;
    ArrayList<Torch> torchList = new ArrayList<>();
    public CreationAddTorches(Chunk map)
    {
        this.map = map;
        createTorches();
    }

    public void createTorches()
    {
        int mapSizeBlocksX = Game.numberOfChunksX * Game.chunkSizeBlocks;
        int mapSizeBlocksY = Game.numberOfChunksY * Game.chunkSizeBlocks;


        float initalSpawnRate = 0.02f;
        for (int a = 0; a<mapSizeBlocksX;a++)
        {
            for (int b = 0; b<mapSizeBlocksY;b++)
            {
                /*
                if ((Math.random() < initalSpawnRate) && map.getBlockAt(a,b).getID() == 1 && map.getBlockAt(a, b+1).getID() == 0)
                {
                    Torch t = new Torch(map.getBlockAt(a,b).getPosition());
                    torchList.add(t);

                }

                 */
                if (map.getBlockAt(a,b).getID() ==1 )
                {
                    if ((Math.random() < calculateProbability(findDistanceToNearestTorch(map.getBlockAt(a,b).getPosition()))))
                    {
                        Torch t = new Torch(map.getBlockAt(a,b).getPosition());
                        torchList.add(t);
                    }
                }

            }

        }
    }
    private float findDistanceToNearestTorch(Vector2f position)
    {
        int numberOfTorchesToCheck = 10; // optimization because as the game scans across the map spawning torches, should only need to check against recent spawns (10 most recent)
        float distance = 10000;
        int iStart = 0;
        if(torchList.size() < numberOfTorchesToCheck)
        {
            iStart = 0;
        }
        else
        {
            iStart = torchList.size() - numberOfTorchesToCheck;
        }
        for (int i = iStart; i < torchList.size(); i++)
        {
            float x =(float)Math.pow((torchList.get(i).getPosition().x - position.x),2);
            float y =(float)Math.pow((torchList.get(i).getPosition().y - position.y),2);
            float  newDistance = (float)Math.pow(x + y, 0.5);

            if (newDistance < distance)
            {
                distance = newDistance;
            }
        }
        return distance;
    }
    private float calculateProbability(float distance)
    {
        return (float)(0.00000001* Math.pow(distance,2));
        /*
        float maxDistance = 500;      //at this distance probability = 1
        float stepDistance = 0;     //factor at which we reach maxDistance
        if(distance > maxDistance)
        {
            return  1;
        }
        else
        {

        }
        return  0;

         */
    }
    public ArrayList<Torch> getTorchList() {
        return torchList;
    }
}
