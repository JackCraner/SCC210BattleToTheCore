package Main.Game.ECS.Factory;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.ECS.Components.Pickup;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StatComponents.Mana;
import Main.Game.ECS.Components.StatComponents.Speed;
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
        g.addComponent(new Position(position,g));
        g.addComponent(new Inputs(InputTypes.HUMAN));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,(byte)3,Entity.PLAYER.textureString, (byte)1));
        g.addComponent(new Speed(MovementTypes.CONTROLLED, 200));
        g.addComponent(new Light());
        g.addComponent(new Health(100));
        g.addComponent(new Mana(100));
        g.addComponent(new Collider(true,true,true));
        g.addComponent(new Backpack(6,true));
        g.addComponent(new XPBar(100));
        g.addComponent(new Animation(0.2f));
        g.addComponent(new EffectComponent());


        //g.getComponent(Stats.class).getComponent(Speed.class).addEffect(new Effects(2,10));
        g.getComponent(EffectComponent.class).addEffect(new Effects(Speed.class,3f,5));
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
        g.addComponent(new Backpack(1, false));
        g.getComponent(Backpack.class).addGameObject(wand());
        g.addComponent(new Health(20));
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.CHEST.textureString));
        g.addComponent(new Collider(true,true,false,false));

        return g;
    }
    public static GameObject block(Vector2f position, byte blockID)
    {
        GameObject g = new GameObject(Entity.BLOCK.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(BLOCKSIZE));
        g.addComponent(new TextureComponent(TextureTypes.BLOCK,Entity.BLOCK.textureString));
        g.getComponent(TextureComponent.class).tileMapLocation = blockID;
        g.getComponent(TextureComponent.class).layer= 0;
        g.addComponent(new Collider());
        return g;
    }
    public static GameObject torch(Vector2f position)
    {
        GameObject g = new GameObject(Entity.TORCH.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.TORCH.textureString));
        g.addComponent(new Light(0.3f,3f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Collider(true,false,false));
        g.addComponent(new Pickup(null,200));
        return g;

    }

    public static GameObject itemFrameWork(Vector2f position, GameObject itemsUse)
    {
        GameObject g = new GameObject(Entity.SWORD.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.SWORD.textureString));
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
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.SWORD.textureString));
        g.addComponent(new Pickup(swordSwoosh(),0.4f));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false));
        return g;

    }
    public static GameObject swordSwoosh()
    {
        GameObject g = new GameObject(Entity.SWORDSWOOSH.name);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.SWORDSWOOSH.textureString));
        g.addComponent(new Damage(10));
        g.addComponent(new LifeSpan(0.25f));
        g.addComponent(new Speed(MovementTypes.LINEAR, 100));
        g.addComponent(new Collider(true,true,false));
        return g;
    }
    public static GameObject fireBall()
    {
        GameObject g = new GameObject(Entity.FIREBALL.name);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.FIREBALL.textureString));
        g.addComponent(new Damage(10));
        g.addComponent(new Light(0.1f,10f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Speed(MovementTypes.LINEAR,500));
        g.addComponent(new LifeSpan(2f));
        g.addComponent(new Collider(true,true,false,false));
        return g;
    }
    public static GameObject wand(Vector2f position)
    {
        GameObject g = new GameObject(Entity.WAND.name);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.WAND.textureString));
        g.addComponent(new Pickup(fireBall(),2));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false,false));
        return g;

    }
    public static GameObject wand()
    {
        GameObject g = new GameObject(Entity.WAND.name);
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.WAND.textureString));
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
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,(byte)2,Entity.ENEMY.textureString));
        g.addComponent(new Collider(true,true,false,false));
        g.addComponent(new Health(100));
        g.addComponent(new Armor(10));
        g.addComponent(new Backpack(6,true));
        //g.addComponent(new Movement(MovementTypes.CONTROLLED,s));
        g.addComponent(new Inputs(InputTypes.AI));
        g.addComponent(new Level(1));
        return g;
    }
    public static GameObject damageNumber(Vector2f position, float dmg)
    {
        GameObject g = new GameObject(Entity.DamageText.name);

        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(20,20)));
        g.getComponent(TransformComponent.class).setRotation(-90);
        g.addComponent(new TextureComponent(TextureTypes.TEXT,(byte)2,String.valueOf((int)dmg)));
        g.addComponent(new LifeSpan(1.5f));
        g.addComponent(new Speed(MovementTypes.LINEAR,30));
       return  g;
    }
    public static GameObject xpOrb(Vector2f position)
    {
        GameObject g = new GameObject(Entity.DamageText.name);

        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(20,20)));
        //g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,(byte)2,Entity.XPORB.textureString));
        g.addComponent(new LifeSpan(10f));
        g.addComponent(new Particles(0.1f));

        return  g;
    }
    public static GameObject genObject(Entity e, Object... o)
    {
        for (Object o1:o)
        {
            //get entity parameters
            //cast the o objects to the parameter types
            //inject the parameters into the addComponents
        }
        return null;

        //new data type called entityParameters<Entity>
        /**
         * entityParameters<PLAYER>
         *     entityParameters(vectory, dmg)
         * </PLAYER>
         */
    }




}
