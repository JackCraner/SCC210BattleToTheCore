package Main.Game.ECS.Factory;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Systems.GameSystem;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Blueprint
{
    public static Vector2f BLOCKSIZE = new Vector2f(32,32);
    public static Vector2f TEXTURESIZE = new Vector2f(32,32);
    public static Vector2f OBJECTSIZE = new Vector2f(32,32);


    public static void addGameObjectToSystems()
    {

    }

    public static GameObject player(Vector2f position)
    {

        GameObject g = new GameObject(EntityID.PLAYER.name, position,OBJECTSIZE);
        g.addComponent(new TextureComponent(EntityID.PLAYER.textureID));
        g.addComponent(new Movement(1));
        g.addComponent(new Collider());
        g.addComponent(new Light());
        g.addComponent(new Shoot());

        return g;


    }
    public static GameObject test(Vector2f position)
    {
        GameObject g = new GameObject(EntityID.PLAYER.name, position, OBJECTSIZE);
        g.addComponent(new Collider());
        return g;
    }
    public static GameObject chest(Vector2f position)
    {
        GameObject g = new GameObject(EntityID.CHEST.name, position,OBJECTSIZE);
        g.addComponent(new TextureComponent(EntityID.CHEST.textureID));

        return g;
    }
    public static GameObject block(Vector2f position, byte blockID)
    {
        GameObject g = new GameObject(EntityID.BLOCK.name, position, BLOCKSIZE);
        g.addComponent(new TextureComponent(EntityID.BLOCK.textureID));
        g.getComponent(TextureComponent.class).tileMapLocation = blockID;
        g.getComponent(TextureComponent.class).layer= 0;
        return g;
    }




}
