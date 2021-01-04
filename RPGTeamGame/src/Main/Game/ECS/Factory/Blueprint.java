package Main.Game.ECS.Factory;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Systems.GameSystem;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Blueprint
{
    public static Vector2f blockSize = new Vector2f(32,32);

    public static void addGameObjectToSystems()
    {

    }

    public static GameObject player(Vector2f position)
    {

        GameObject g = new GameObject(EntityID.PLAYER.name);
        g.addComponent(new Size(blockSize));
        g.addComponent(new Position(position));

        g.addComponent(new TextureComponent(EntityID.PLAYER.textureID));
        g.addComponent(new Movement(10));
        g.addComponent(new Collider());

        return g;
    }
    public static GameObject test(Vector2f position)
    {
        GameObject g = new GameObject(EntityID.PLAYER.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(blockSize));
        g.addComponent(new Collider());
        return g;
    }
    public static GameObject chest(Vector2f position)
    {
        GameObject g = new GameObject(EntityID.CHEST.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(blockSize));
        g.addComponent(new TextureComponent(EntityID.CHEST.textureID));

        return g;
    }
    public static GameObject block(Vector2f position, byte blockID)
    {
        GameObject g = new GameObject(EntityID.BLOCK.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(blockSize));
        g.addComponent(new TextureComponent(EntityID.BLOCK.textureID));
        g.getComponent(TextureComponent.class).tileMapLocation = blockID;
        g.getComponent(TextureComponent.class).layer= 0;
        return g;
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