package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.ECS.Components.Pickup;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.Game;
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
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent((byte)3,Entity.PLAYER.textureString));
        g.addComponent(new Movement(MovementTYPES.CONTROLLED, 200));
        g.addComponent(new Light());
        g.addComponent(new Collider(true,true,true,g));
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
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        return g;
    }
    public static GameObject chest(Vector2f position)
    {
        GameObject g = new GameObject(Entity.CHEST.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.CHEST.textureString));

        return g;
    }
    public static GameObject block(Vector2f position, byte blockID)
    {
        GameObject g = new GameObject(Entity.BLOCK.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(BLOCKSIZE));
        g.addComponent(new TextureComponent(Entity.BLOCK.textureString));
        g.getComponent(TextureComponent.class).tileMapLocation = blockID;
        g.getComponent(TextureComponent.class).layer= 0;
        return g;
    }
    public static GameObject torch(Vector2f position)
    {
        GameObject g = new GameObject(Entity.TORCH.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.TORCH.textureString));
        g.addComponent(new Light(0.3f,3f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Collider(true,false,false));
        g.addComponent(new Pickup(null,200));
        return g;

    }
    public static GameObject torchItem()
    {
        GameObject g = new GameObject(Entity.TORCH.name);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.TORCH.textureString));
        g.addComponent(new Light(0.3f,3f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Collider(true,false,false));
        return g;
    }

    public static GameObject itemFrameWork(Vector2f position, GameObject itemsUse)
    {
        GameObject g = new GameObject(Entity.SWORD.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(Entity.SWORD.textureString));
        g.addComponent(new Pickup(itemsUse,150));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false));
        return g;

    }
    public static GameObject sword(Vector2f position)
    {
        GameObject g = new GameObject(Entity.SWORD.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(Entity.SWORD.textureString));
        g.addComponent(new Pickup(swordSwoosh(),0.4f));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false));
        return g;

    }
    public static GameObject swordSwoosh()
    {
        GameObject g = new GameObject(Entity.SWORDSWOOSH.name);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.SWORDSWOOSH.textureString));
        g.addComponent(new Damage(10));
        g.addComponent(new LifeSpan(0.25f));
        g.addComponent(new Movement(MovementTYPES.LINEAR, 100f));
        g.addComponent(new Collider(true,false,false));
        return g;
    }
    public static GameObject fireBall()
    {
        GameObject g = new GameObject(Entity.FIREBALL.name);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(Entity.FIREBALL.textureString));
        g.addComponent(new Damage(10));
        g.addComponent(new Light(0.1f,10f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Movement(MovementTYPES.LINEAR, 500));
        g.addComponent(new LifeSpan(2f));
        g.addComponent(new Collider(true,true,true,true));
        return g;
    }
    public static GameObject wand(Vector2f position)
    {
        GameObject g = new GameObject(Entity.WAND.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(Entity.WAND.textureString));
        g.addComponent(new Pickup(fireBall(),2));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false,false));
        return g;

    }
    public static GameObject enemy(Vector2f position)
    {
        GameObject g = new GameObject(Entity.ENEMY.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent((byte)2,Entity.ENEMY.textureString));
        g.addComponent(new HealthBar(100));
        g.addComponent(new Collider(true,true,true,false));
        g.addComponent(new Backpack(6));
        g.addComponent(new Movement(MovementTYPES.AI,0));
        return g;
    }



}
