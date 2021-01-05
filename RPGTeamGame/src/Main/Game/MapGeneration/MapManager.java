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
    public static MapManager getInstance()
    {
        return mapInstance;
    }

    private ArrayList<GameObject> generateBackground()
    {
        ArrayList<GameObject> backgroundList = new ArrayList<>();
        HashMap<Byte,Byte> binaryToTextureID = new HashMap<>();
        binaryToTextureID.put(CellularAutomata.WALLID,(byte)25);
        binaryToTextureID.put(CellularAutomata.EMPTYID,(byte)31);

        CellularAutomata.getInstance().generateBinaryMapping();
        byte[][] binaryMapping = CellularAutomata.getInstance().getBinaryMapping();

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
                backgroundList.add(g);

            }
        }


        return backgroundList;
    }

    public void generateOptimizedBackground()
    {
        CellularAutomata.getInstance().generateBinaryMapping();
        byte[][] binaryMapping = CellularAutomata.getInstance().getBinaryMapping();





    }
    public ArrayList<GameObject> generateMap()
    {
        ArrayList<GameObject> mapGameObjects = new ArrayList<>();
        mapGameObjects.addAll(generateBackground());


        return mapGameObjects;
    }




}
