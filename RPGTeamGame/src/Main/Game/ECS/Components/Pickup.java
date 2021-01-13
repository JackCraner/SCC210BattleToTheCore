package Main.Game.ECS.Components;

import Main.Game.ECS.Components.Items.ItemEffect;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Pickup extends Component
{
    /*
    private ArrayList<ItemEffect> itemEffects = new ArrayList<>();
    public Pickup(ItemEffect... effects)
    {
        for (ItemEffect e: effects)
        {
            itemEffects.add(e);
        }
    }

    public ArrayList<ItemEffect> getItemEffects() {
        return itemEffects;
    }

     */
    //give it a Use case whether it is a sword swing or eat of an apple

    private GameObject attachedTo = null;
    private GameObject spawns;
    private float cooldownTime;
    private float cooldownValue = 0;
    public Pickup(GameObject spawns, float cooldownTime)
    {
        this.spawns = spawns;
        this.cooldownTime = cooldownTime;
    }

    public GameObject getSpawns(Vector2f position)
    {
        cooldownValue = cooldownTime;
        GameObject newSpawn = spawns.clone();
        newSpawn.addComponent(new Position(position,newSpawn));
        return newSpawn;
    }
    public Boolean doesSpawn()
    {
        return spawns != null;
    }
    public void attach(GameObject g)
    {
        attachedTo =g;
        if (doesSpawn())
        {
            spawns.getComponent(Collider.class).avoidGameObject = g;
        }

    }
    public void reduceCoolDown(float dt)
    {
        if (cooldownValue >0)
        {
            cooldownValue -= dt;
        }
    }

    public Boolean isReady()
    {
        return (cooldownValue <= 0);
    }

    @Override
    public Component clone() {
        return new Pickup(spawns.clone(),cooldownTime);
    }
}