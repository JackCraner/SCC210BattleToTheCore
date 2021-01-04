package Main.Game.MapGeneration;

import Main.Game.ECS.Components.Collider;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;

public class MapManager
{
    private static MapManager mapInstance = new MapManager();
    public static int MAPSIZE_X = 10;
    public static int MAPSIZE_Y = 10;
    public static MapManager getInstance()
    {
        return mapInstance;
    }

    private ArrayList<GameObject> generateBackground()
    {
        ArrayList<GameObject> backgroundList = new ArrayList<>();
        HashMap<Byte,Byte> binaryToTextureID = new HashMap<>();
        binaryToTextureID.put((byte)0,(byte)25);
        binaryToTextureID.put((byte)1,(byte)31);
        for(int a = 0; a < MAPSIZE_X; a++)
        {
            for(int b = 0; b< MAPSIZE_Y; b++)
            {

                backgroundList.addAll(generateChunk(new Vector2f(a,b), binaryToTextureID));

            }
        }


        return backgroundList;
    }
    private ArrayList<GameObject> generateChunk(Vector2f chunkPos, HashMap<Byte, Byte> binaryToTextureID)
    {
        ArrayList<GameObject> chunkList = new ArrayList<>();
        byte[][] binaryMapping = CellularAutomata.getInstance().generateBinaryMapping();
        for(int a = 0; a < CellularAutomata.CHUNKSIZEBLOCKS; a++)
        {
            for(int b =0; b< CellularAutomata.CHUNKSIZEBLOCKS; b++)
            {

                Vector2f pos = new Vector2f(a * Blueprint.blockSize.x + chunkPos.x * CellularAutomata.CHUNKSIZEPIXELS, b*Blueprint.blockSize.y + chunkPos.y * CellularAutomata.CHUNKSIZEPIXELS);

                GameObject g = Blueprint.block(pos,binaryToTextureID.get(binaryMapping[a][b]));            //too expensive

                if (binaryMapping[a][b] == CellularAutomata.WALLID)
                {
                  // g.addComponent(new Collider());

                }
                chunkList.add(g);

            }
        }
        return chunkList;
    }

    public ArrayList<GameObject> generateMap()
    {
        ArrayList<GameObject> mapGameObjects = new ArrayList<>();





        mapGameObjects.addAll(generateBackground());


        return mapGameObjects;
    }




}
