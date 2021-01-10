package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.Pickup;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector3f;

public class Blueprint
{
    public static Vector2f BLOCKSIZE = new Vector2f(32,32);
    public static Vector2f TEXTURESIZE = new Vector2f(32,32);
    public static Vector2f OBJECTSIZE = new Vector2f(32,32);
    public static Vector2f ITEMSIZE = new Vector2f(16,16);

    public static void addGameObjectToSystems()
    {

    }

    public static GameObject player(Vector2f position)
    {

        GameObject g = new GameObject(Entity.PLAYER.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(OBJECTSIZE));
        g.addComponent(new TextureComponent((byte)2,Entity.PLAYER.textureString));
        g.addComponent(new Movement(1));
        g.addComponent(new Light());
        g.addComponent(new Collider(true,true,true,true));
        g.addComponent(new Backpack(6));
        g.addComponent(new HealthBar(100));
        g.addComponent(new ManaBar(100));
        g.addComponent(new XPBar(100));
        //g.addComponent(new Shoot());

        return g;


    }
    public static GameObject test(Vector2f position)
    {
        GameObject g = new GameObject(Entity.CHEST.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(OBJECTSIZE));
        return g;
    }
    public static GameObject chest(Vector2f position)
    {
        GameObject g = new GameObject(Entity.CHEST.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.CHEST.textureString));

        return g;
    }
    public static GameObject block(Vector2f position, byte blockID)
    {
        GameObject g = new GameObject(Entity.BLOCK.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(BLOCKSIZE));
        g.addComponent(new TextureComponent(Entity.BLOCK.textureString));
        g.getComponent(TextureComponent.class).tileMapLocation = blockID;
        g.getComponent(TextureComponent.class).layer= 0;
        return g;
    }
    public static GameObject torch(Vector2f position)
    {
        GameObject g = new GameObject(Entity.TORCH.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.TORCH.textureString));
        g.addComponent(new Light(0.3f,3f, new Vector3f(1f,0.8f,0.2f)));
        return g;

    }
    public static GameObject item(Vector2f position)
    {
        GameObject g = new GameObject(Entity.ITEM.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(ITEMSIZE));
        g.addComponent(new TextureComponent(Entity.ITEM.textureString));
        g.addComponent(new Pickup(swordSwoosh(position),1000));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false,false));
        return g;

    }
    public static GameObject swordSwoosh(Vector2f position)
    {
        GameObject g = new GameObject(Entity.SWORDSWOOSH.name);
        g.addComponent(new Position(position));
        g.addComponent(new Size(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.SWORDSWOOSH.textureString));
        g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false,false));
        return g;
    }



}
