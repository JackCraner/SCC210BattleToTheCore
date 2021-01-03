package Main.Game.MapGeneration;

import Main.Game.ECS.Components.BoxCollider;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.Size;
import Main.Game.ECS.Components.TextureComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.EntityID;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapManager
{
    private static MapManager mapInstance = new MapManager();

    public static MapManager getInstance()
    {
        return mapInstance;
    }

    private ArrayList<GameObject> generateBackground(byte[][] binaryMapping)
    {
        ArrayList<GameObject> backgroundList = new ArrayList<>();
        HashMap<Byte,Byte> binaryToTextureID = new HashMap<>();
        binaryToTextureID.put((byte)0,(byte)25);
        binaryToTextureID.put((byte)1,(byte)31);
        for(int a = 0; a < CellularAutomata.CHUNKSIZEBLOCKS; a++)
        {
            for(int b =0; b< CellularAutomata.CHUNKSIZEBLOCKS; b++)
            {

                Vector2f pos = new Vector2f(a * Blueprint.blockSize.x, b*Blueprint.blockSize.y);

                GameObject g = Blueprint.block(pos,binaryToTextureID.get(binaryMapping[a][b]));            //too expensive

                if (binaryMapping[a][b] == CellularAutomata.WALLID)
                {
                    g.addComponent(new BoxCollider());

                }
               backgroundList.add(g);

            }
        }


        return backgroundList;
    }

    public ArrayList<GameObject> generateMap()
    {
        ArrayList<GameObject> mapGameObjects = new ArrayList<>();

        byte[][] binaryMapping = CellularAutomata.getInstance().generateBinaryMapping();



        mapGameObjects.addAll(generateBackground(binaryMapping));


        return mapGameObjects;
    }




}
