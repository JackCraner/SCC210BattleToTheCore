package Main.Game.ECS.Factory;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Systems.GameSystem;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Blueprint
{

    public static GameObject createGameObject(String[] entityData, Vector2f position)
    {

        GameObject newObject = new GameObject(entityData[0]);
        newObject.addComponent(new Position(position));
        newObject.addComponent(new SpriteController(EntityID.getTexture(entityData[1]),position));
        newObject.addComponent(new NearPlayerFlag());

        newObject.getComponent(NearPlayerFlag.class).npFlag = true;

        if(entityData == EntityID.CHEST)
        {
            createChest(newObject);
        }
        else if(entityData == EntityID.PLAYER)
        {
            createPlayer(newObject);
        }
        return newObject;

    }

    public static void addGameObjectToSystems()
    {

    }

    public static void createPlayer(GameObject g)
    {
        g.addComponent(new PlayerController());
        g.addComponent(new Movement(1));
        g.addComponent(new BoxCollider());
    }

    public static void createChest(GameObject g)
    {
       // g.addComponent(new Movement(2));
    }


    public static Component[] getCompatibleComponents(GameObject g, GameSystem s)
    {
        ArrayList<Component> c = new ArrayList<>();
        for (Class<? extends  Component> sc: s.systemComponentRequirements())
        {
            boolean componentContained = false;
            for(Component gc : g.getComponentList())
            {
                if (gc.getClass() == sc)
                {
                    c.add(gc);
                    componentContained = true;
                }
            }
            if (!componentContained)
            {
                return null;
            }
        }
        return c.toArray(Component[]::new);

    }

}
