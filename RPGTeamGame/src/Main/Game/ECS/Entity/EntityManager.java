package Main.Game.ECS.Entity;

import Main.Game.ECS.Components.Collider;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.TextureComponent;
import Main.Game.QuadTree.QTRoot;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;



//should always take in global positions (pixel positions)


public class EntityManager
{
    private static EntityManager entityManagerInstance = new EntityManager();
    private static QTRoot quadTree = QTRoot.getQuadTree();
    public HashMap<Class<? extends Component>, Integer> componentBitMask = new HashMap<>();

    public static EntityManager getEntityManagerInstance() {
        return entityManagerInstance;
    }




    public static Vector2i convertGlobalPositionTOLEAF(Vector2f position)
    {
        return new Vector2i(Math.floorDiv((int)position.x,quadTree.LEAFSIZE), Math.floorDiv((int)position.y,quadTree.LEAFSIZE));
    }
    public void addGameObject(GameObject g)
    {
        quadTree.addGameObject(g, convertGlobalPositionTOLEAF(g.getPosition()));
    }
    public void addGameObject(ArrayList<GameObject> gArray)
    {
        for (GameObject g:gArray)
        {
            quadTree.addGameObject(g, convertGlobalPositionTOLEAF(g.getPosition()));
        }

    }
    private ArrayList<GameObject> getGameObjectsInLeaf(Vector2i pos)
    {

        if ((pos.x < quadTree.NUMLEAFX && pos.x >= 0) && (pos.y < quadTree.NUMLEAFY && pos.y >= 0))
        {
            return quadTree.getLeaf(pos).getObjectList();
        }
        return new ArrayList<>();
    }
    public ArrayList<GameObject> getGameObjectInVicinity(Vector2f pos, float range)
    {

        int leafRange = (int)Math.ceil(range/quadTree.LEAFSIZE);


        ArrayList<GameObject> newList = new ArrayList<>();
        Vector2i cPos = convertGlobalPositionTOLEAF(pos);
        for (int a = -leafRange; a <= leafRange; a++)
        {
            for (int b = -leafRange; b <= leafRange; b++)
            {
                newList.addAll(getGameObjectsInLeaf(new Vector2i(cPos.x + a, cPos.y + b)));
            }
        }

        return newList;
    }

    public void updateLeaf(GameObject g, Vector2f newPosition)
    {
        if (convertGlobalPositionTOLEAF(g.getPosition()) != convertGlobalPositionTOLEAF(newPosition))
        {
            quadTree.removeGameObject(g,convertGlobalPositionTOLEAF(g.getPosition()));
            quadTree.addGameObject(g,convertGlobalPositionTOLEAF(newPosition));
        }
    }






    private EntityManager()
    {
        componentBitMask.put(TextureComponent.class,Integer.parseInt("1", 2));
        componentBitMask.put(Collider.class,Integer.parseInt("10", 2));
        componentBitMask.put(Movement.class,Integer.parseInt("100", 2));

    }
    public int produceBitMask(int... bitMasks)
    {
        int newMask = 0;
        for (int i: bitMasks)
        {
            newMask |= i;
        }
        return newMask;
    }

    public int produceBitMask(Class<? extends Component>... bitMasks)
    {
        int newMask = 0;
        for (Class<? extends  Component> c : bitMasks)
        {
            newMask |= componentBitMask.get(c);
        }
        return newMask;
    }






}
