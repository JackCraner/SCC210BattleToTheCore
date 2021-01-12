package Main.Game.ECS.Components;


import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class Backpack extends Component
{
    private ArrayList<GameObject> gameObjectsINBACKPACK = new ArrayList<>();
    private int maxNumObject = 1;

    private float emptyCooldown;


    public Backpack(int size)
    {
        maxNumObject = size;
    }
    public Backpack(GameObject g)
    {
        gameObjectsINBACKPACK.add(g);
    }
    public Backpack(int size, ArrayList<GameObject> g)
    {
        this.maxNumObject = size;
        gameObjectsINBACKPACK.addAll(g);
    }
    public void addGameObject(GameObject g)
    {

        gameObjectsINBACKPACK.add(g);

    }

    public float getEmptyCooldown() {
        return emptyCooldown;
    }

    public void setEmptyCooldown(float emptyCooldown) {
        this.emptyCooldown = emptyCooldown;
    }

    public Boolean inventoryHasSpace()
    {
        return gameObjectsINBACKPACK.size() < maxNumObject;
    }
    public ArrayList<GameObject> getObjectsINBACKPACK()
    {
        return gameObjectsINBACKPACK;
    }

    @Override
    public Component clone()
    {
        return new Backpack(maxNumObject,(ArrayList<GameObject>)gameObjectsINBACKPACK.clone());
    }
}
