package Main.Game.ECS.Components;


import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class Backpack extends Component
{
    private ArrayList<GameObject> gameObjectsINBACKPACK = new ArrayList<>();
    private int maxNumObject = 1;
    private float emptyCooldown;

    private final Boolean canUseItems;
    private final Boolean canDropItems;

    public Backpack(int size, Boolean canUseItems)
    {
        maxNumObject = size;
        this.canUseItems = canUseItems;
        this.canDropItems = canUseItems;
    }
    public Backpack(GameObject g, Boolean canUseItems)
    {
        gameObjectsINBACKPACK.add(g);
        this.canUseItems = canUseItems;
        this.canDropItems = canUseItems;
    }
    public Backpack(int size, ArrayList<GameObject> g, Boolean canUseItems)
    {
        this.maxNumObject = size;
        gameObjectsINBACKPACK.addAll(g);
        this.canUseItems = canUseItems;
        this.canDropItems = canUseItems;
    }
    public void addGameObject(GameObject g)
    {

        gameObjectsINBACKPACK.add(g);

    }

    public Boolean getCanUseItems() {
        return canUseItems;
    }
    public Boolean getCanDropItems()
    {
        return canDropItems;
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
        return new Backpack(maxNumObject,(ArrayList<GameObject>)gameObjectsINBACKPACK.clone(), canUseItems);
    }
}
