package Main.Game.Background.MapGen.MapCreation.MapStylization;

import Main.Game.Background.MapGen.Chunks.Chunk;
import Main.Game.ForeGround.Entities.Chest;
import Main.Game.ForeGround.Entities.Entity;
import Main.Game.ForeGround.Entities.SaveShrine;
import Main.Game.ForeGround.Entities.Torch;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreationFixtures
{
    Chunk map;
    ArrayList<Entity> entityList = new ArrayList<>();

    public CreationFixtures(Chunk map)
    {
        this.map = map;
        generateEntities();
        System.out.println("SaveShrines Created: " + entityTypeCount(SaveShrine.class));
        System.out.println("Chests Created: " + entityTypeCount(Chest.class));
        System.out.println("Torches Created: " + entityTypeCount(Torch.class));
    }
    public void generateEntities()
    {
        Class[] entitiesToCreate = {Chest.class,Torch.class,SaveShrine.class};
        HashMap<Class, ArrayList<Float>> entitySpawnConditions = new HashMap<>();
        entitySpawnConditions.put(Chest.class, new ArrayList<>(Arrays.asList(1f,0.00000003f)));    //size of entity, spawnRate
        entitySpawnConditions.put(Torch.class, new ArrayList<>(Arrays.asList(0f,0.00000001f)));
        entitySpawnConditions.put(SaveShrine.class, new ArrayList<>(Arrays.asList(2f,0.0000000005f)));
        int mapSizeBlocksX = Game.numberOfChunksX * Game.CHUNKSIZEBLOCKS;
        int mapSizeBlocksY = Game.numberOfChunksY * Game.CHUNKSIZEBLOCKS;



            for (int a = 0; a<mapSizeBlocksX;a++)
            {
                for (int b = 0; b < mapSizeBlocksY - 1; b++)
                {
                    for (Class entityClass: entitiesToCreate)
                    {

                        if (checkBlock(new Vector2i(a, b), entitySpawnConditions.get(entityClass).get(0))) {
                            Vector2f newPosition = map.getBlockAt(a, b).getPosition();
                            if (Math.random() < calculateProbability(findNearest(newPosition, entityClass, entityList), entitySpawnConditions.get(entityClass).get(1))) {
                                try {
                                    Constructor<Entity> cons = entityClass.getConstructor(Vector2f.class);
                                    entityList.add(cons.newInstance(newPosition));
                                    break;
                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                            }
                        }
                    }

                }
            }


    }

    public float findNearest(Vector2f position, Class<?> objectType, ArrayList<Entity> eList)
    {
        int numberToCheck = 30; // optimization because as the game scans across the map spawning torches, should only need to check against recent spawns (10 most recent)
        float distance = 10000;
        int iStart = 0;
        if(eList.size() < numberToCheck)
        {
            iStart = 0;
        }
        else
        {
            iStart = eList.size() - numberToCheck;
        }
        for (int i = iStart; i < eList.size(); i++)
        {

            if (objectType == eList.get(i).getClass())
            {
                float x =(float)Math.pow((eList.get(i).getPosition().x - position.x),2);
                float y =(float)Math.pow((eList.get(i).getPosition().y - position.y),2);
                float  newDistance = (float)Math.pow(x + y, 0.5);

                if (newDistance < distance)
                {
                    distance = newDistance;
                }
            }

        }
        return distance;
    }
    public float calculateProbability(float distance, float multiplier)
    {

        return (float)(multiplier* Math.pow(distance,2));
    }

    public boolean checkBlock(Vector2i position, Float heightNeeded)
    {
        if (map.getBlockAt(position).getID() == 1)
        {
            if (heightNeeded == 0)
            {
                return true;
            }
            else
            {
                Boolean valid = true;
                try
                {
                   for (int a =0; a < heightNeeded; a++)
                   {
                       if (!(map.getBlockAt(position.x + a, position.y + Math.round(heightNeeded)).getID() == 0 && (map.getBlockAt(position.x + a, position.y + Math.round(heightNeeded) -1).getID() == 1)))
                       {
                           valid = false;
                       }
                   }
                   return valid;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        return false;


    }
    public int entityTypeCount(Class<?> objectType)
    {
        int count = 0;
        for(Entity e: entityList)
        {
            if (objectType == e.getClass())
            {
                count ++;
            }
        }
        return count;
    }
    public ArrayList<Entity> getEntityList() {
        return entityList;
    }
}
