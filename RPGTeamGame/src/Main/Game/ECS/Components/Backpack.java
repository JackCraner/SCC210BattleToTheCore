package Main.Game.ECS.Components;


import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class Backpack extends Component
{
    private ArrayList<GameObject> gameObjectsINBACKPACK = new ArrayList<>();
    private int maxNumObject = 1;
    public Backpack(int size)
    {
        maxNumObject = size;
    }
    public Backpack(GameObject g)
    {
        gameObjectsINBACKPACK.add(g);
    }
    public void addGameObject(GameObject g)
    {

        gameObjectsINBACKPACK.add(g);

    }
    public Boolean inventoryHasSpace()
    {
        return gameObjectsINBACKPACK.size() < maxNumObject;
    }
    public ArrayList<GameObject> getObjectsINBACKPACK()
    {
        return gameObjectsINBACKPACK;
    }

}
