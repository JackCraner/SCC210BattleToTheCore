package Main.Game.MapGeneration;

import Main.Game.ECS.Components.Collider;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapBlueprint
{
    private Random rng = new Random();
    private Map mapID;
    private byte[][] binaryMapping;

    public MapBlueprint(int seed, Map mapID)
    {
        int count = 0;
        rng.setSeed(seed);
        this.mapID = mapID;
        binaryMapping = CellularAutomata.getInstance().generateBinaryMapping(rng);
        HashMap<Byte,Byte> binaryToTextureID = new HashMap<>();
        binaryToTextureID.put(CellularAutomata.WALLID,mapID.wallTextureID);
        binaryToTextureID.put(CellularAutomata.EMPTYID,mapID.emptyTextureID);


        for(int a = 0; a < CellularAutomata.CHUNKSIZEBLOCKSX; a++)
        {
            for(int b =0; b< CellularAutomata.CHUNKSIZEBLOCKSY; b++)
            {

                Vector2f pos = new Vector2f(a * Blueprint.BLOCKSIZE.x, b*Blueprint.BLOCKSIZE.y);
                GameObject g = Blueprint.block(pos,binaryToTextureID.get(binaryMapping[a][b]));            //too expensive
                if (binaryMapping[a][b] == CellularAutomata.WALLID)
                {
                    g.addComponent(new Collider());

                }
                else
                {
                    if (CellularAutomata.checkNeighbours(binaryMapping,a,b) == 9)
                    {
                        if ( rng.nextFloat() < generateProbability(mapID.torchSpawnRate, findDistance(pos, Entity.TORCH.name), mapID.torchMinDistance))
                        {
                            EntityManager.getEntityManagerInstance().addGameObject(Blueprint.torch(pos));
                            count ++;
                        }
                    }
                    if(b < CellularAutomata.CHUNKSIZEBLOCKSY - 1 && b > 0 && binaryMapping[a][b + 1] == CellularAutomata.WALLID&& binaryMapping[a][b -1] == CellularAutomata.EMPTYID)
                    {
                        if ( rng.nextFloat() < generateProbability(mapID.chestSpawnRate, findDistance(pos, Entity.CHEST.name), mapID.chestMinDistance))
                        {
                            EntityManager.getEntityManagerInstance().addGameObject(Blueprint.chest(pos));
                        }
                    }
                }



                EntityManager.getEntityManagerInstance().addGameObject(g);


            }
        }
        System.out.println(count);
    }
    public MapBlueprint(Map mapID)
    {
        this(new Random().nextInt(), mapID);
    }
    public MapBlueprint()
    {
        this(Map.MAP1);

    }

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
    public float generateProbability(float exponent, float distance, float minDistance)
    {

        return (float)(Math.pow((1 + (exponent/10000)), (distance -minDistance))-1);
    }




}
