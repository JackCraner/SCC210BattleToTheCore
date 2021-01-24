package Main.Game.MapGeneration;

import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Factory Pattern
 * Takes a MapID and a seed to generate a map
 *  - if given no seed, it will randomly generate and store a seed
 */
public class MapBlueprint
{
    private Random rng = new Random();
    private Map mapID;
    private byte[][] binaryMapping;

    /**
     * Generates the map by first calling the Cellular Automata instance to generate the skeleton of the map
     * Then Generates GameObjects based off the skeleton
     *  - Wall blocks where the skeleton defines
     *  - chest blocks randomly in empty space at a ratio given by the Map Enum
     *  - Torches randomly in empty space as well at a ratio given by the Map Enum
     * @param seed The Map Seed
     * @param mapID The Map enum Id
     *              Two maps with the same Seed and MapID wil always look Identical
     */
    public MapBlueprint(int seed, Map mapID)
    {
        int count = 0;
        rng.setSeed(seed);
        this.mapID = mapID;
        binaryMapping = CellularAutomata.getInstance().generateBinaryMapping(rng);
        HashMap<Byte,Byte> binaryToTextureID = new HashMap<>();
        binaryToTextureID.put(CellularAutomata.WALLID,mapID.wallTextureID);
        binaryToTextureID.put(CellularAutomata.EMPTYID,mapID.emptyTextureID);


        for(int a = 1; a < CellularAutomata.CHUNKSIZEBLOCKSX; a++)
        {
            for(int b =1; b< CellularAutomata.CHUNKSIZEBLOCKSY; b++)
            {

                Vector2f pos = new Vector2f(a * Blueprint.BLOCKSIZE.x, b*Blueprint.BLOCKSIZE.y);

                if (binaryMapping[a][b] == CellularAutomata.WALLID)
                {
                    EntityManager.getEntityManagerInstance().addGameObject(Blueprint.block(pos,findWallAngle(binaryMapping,a,b),95));






                }
                else
                {
                    if (CellularAutomata.checkNeighbours8(binaryMapping,a,b) == 9)
                    {
                        if ( rng.nextFloat() < generateProbability(mapID.torchSpawnRate, findDistance(pos, Entity.TORCH.name), mapID.torchMinDistance))
                        {
                            //EntityManager.getEntityManagerInstance().addGameObject(Blueprint.torch(pos));
                            count ++;
                        }

                    }
                    if(b < CellularAutomata.CHUNKSIZEBLOCKSY - 1 && b > 0 && binaryMapping[a][b + 1] == CellularAutomata.WALLID&& binaryMapping[a][b -1] == CellularAutomata.EMPTYID)
                    {
                        //if ( rng.nextFloat() < generateProbability(mapID.chestSpawnRate, findDistance(pos, Entity.CHEST.name), mapID.chestMinDistance))
                        //{
                        //    EntityManager.getEntityManagerInstance().addGameObject(Blueprint.chest(pos));
                       // }
                    }
                }






            }
        }
        //add outer wall
        for(int a = 0; a < CellularAutomata.CHUNKSIZEBLOCKSX; a++)
        {
            EntityManager.getEntityManagerInstance().addGameObject( Blueprint.block(new Vector2f(a * Blueprint.BLOCKSIZE.x,0), (byte) 3,95));
            EntityManager.getEntityManagerInstance().addGameObject( Blueprint.block(new Vector2f(a * Blueprint.BLOCKSIZE.x,CellularAutomata.CHUNKSIZEBLOCKSY * Blueprint.BLOCKSIZE.y),(byte) 3,185));
        }
        for(int b =0; b< CellularAutomata.CHUNKSIZEBLOCKSY; b++)
        {
            EntityManager.getEntityManagerInstance().addGameObject( Blueprint.block(new Vector2f(CellularAutomata.CHUNKSIZEBLOCKSX * Blueprint.BLOCKSIZE.x,b * Blueprint.BLOCKSIZE.y),(byte) 5, 95));
            EntityManager.getEntityManagerInstance().addGameObject( Blueprint.block(new Vector2f(0,b * Blueprint.BLOCKSIZE.y),(byte) 1, 95));
        }
        placeDoor(binaryMapping);
        System.out.println(count);
    }

    /**
     * Generates a map from a randomly generated seed given a MapID
     * @param mapID the Map Enum ID
     */
    public MapBlueprint(Map mapID)
    {
        this(new Random().nextInt(), mapID);
    }
    public MapBlueprint()
    {
        this(Map.MAP1);

    }

    /**
     * To Ensure no chests or torches spawn next to each other and spawn at nice distances
     * the probability a Fixture GameObject spawns is a function of bias probability based of parameters of
     * distance from next nearest similar Fixture and the spawnRate for the fixture in the mapID
     *
     * -- Meaning a torch/ chest should never spawn next to each other
     * -- Meaning a torch/ chest should always spawn at least X distance apart (No void spaces)
     * @param position Position being checked
     * @param name  Name of the fixture being checked
     * @return Distance from the next nearest fixture of the same name
     */
    public float findDistance(Vector2f position, String name)
    {
        float shortestDistance = 1000000;
        ArrayList<GameObject> localEntities = EntityManager.getEntityManagerInstance().getGameObjectInVicinity(position,500);
        for (int i = 0; i < localEntities.size(); i++)
        {
            if(localEntities.get(i).getName() == name)
            {
                Vector2f mapObjectPosition = localEntities.get(i).getComponent(Position.class).getPosition();
                float x =(float)Math.pow((mapObjectPosition.x - position.x),2);
                float y =(float)Math.pow((mapObjectPosition.y - position.y),2);
                shortestDistance = Math.min((float)Math.pow(x + y, 0.5),shortestDistance);

            }
        }
        //System.out.println(shortestDistance);
        return shortestDistance;

    }

    /**
     * Function of bias probability given the parameters
     * @param exponent value within the MapID
     * @param distance distance found from FindDistance()
     * @param minDistance the minimum spawn distance
     * @return the probability value between 0 and 1
     */
    public float generateProbability(float exponent, float distance, float minDistance)
    {

        return (float)(Math.pow((1 + (exponent/10000)), (distance -minDistance))-1);
    }
    public void placeDoor(byte[][] binaryMapping)
    {
        int randX = rng.nextInt(CellularAutomata.CHUNKSIZEBLOCKSX-1);
        int randY = rng.nextInt(10);
        randY =(CellularAutomata.CHUNKSIZEBLOCKSY - randY);


        if (CellularAutomata.checkNeighbours8(binaryMapping,randX,randY) == 9)
        {
            System.out.println(randX + "  " + randY);
            randY =(int) Blueprint.BLOCKSIZE.y * randY;
            randX =(int) Blueprint.BLOCKSIZE.x * randX;
            EntityManager.getEntityManagerInstance().addGameObject(Blueprint.trapdoor(new Vector2f(randX,randY)));
        }
        else
        {
            placeDoor(binaryMapping);
        }
    }

    public byte findWallAngle(byte[][] binaryMapping, int x, int y)
    {
        //Tile rules
        byte tilemapLocation=0;
        try
        {
            if (binaryMapping[x][y+1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation =6;
                Vector2f pos = new Vector2f(x * Blueprint.BLOCKSIZE.x, y*Blueprint.BLOCKSIZE.y);
                if ( rng.nextFloat() < generateProbability(mapID.torchSpawnRate, findDistance(pos, Entity.TORCH.name), mapID.torchMinDistance))
                {
                    EntityManager.getEntityManagerInstance().addGameObject(Blueprint.torch(pos));
                }
                if ( rng.nextFloat() < generateProbability(mapID.chestSpawnRate, findDistance(pos, Entity.CHEST.name), mapID.chestMinDistance))
                {
                    EntityManager.getEntityManagerInstance().addGameObject(Blueprint.chest(new Vector2f(pos.x, pos.y + Blueprint.BLOCKSIZE.y)));
                }

            }

            else if(binaryMapping[x-1][y] == CellularAutomata.EMPTYID && binaryMapping[x][y-1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=2;

            }
            else if(binaryMapping[x-1][y] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=1;

            }
            else if(binaryMapping[x][y-1] == CellularAutomata.EMPTYID && binaryMapping[x+1][y] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=4;

            }
            else if (binaryMapping[x+1][y+1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=5;

            }
            else if (binaryMapping[x-1][y+1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=1;

            }

            else if(binaryMapping[x+1][y] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=5;

            }
            else if(CellularAutomata.checkNeighbours4(binaryMapping,x,y) != 0)
            {
                tilemapLocation=3;

            }
        }
        catch(Exception e)
        {

        }
        return (tilemapLocation);


    }




}
