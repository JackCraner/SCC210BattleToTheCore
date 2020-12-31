package Main.Entity.Factory;

import Main.Entity.Components.BoxCollider;
import Main.Entity.Components.Movement;
import Main.Entity.Components.PlayerController;
import Main.Entity.Components.SpriteController;
import Main.Entity.GameObject;
import org.jsfml.system.Vector2f;

public class Blueprint
{

    public static GameObject createGameObject(String[] entityData, Vector2f position)
    {

        GameObject newObject = new GameObject(entityData[0], position);
        newObject.addComponent(new SpriteController(EntityID.getTexture(entityData[1])));
        if(entityData == EntityID.CHEST)
        {

        }
        else if(entityData == EntityID.PLAYER)
        {
            createPlayer(newObject);
        }
        return newObject;

    }

    public static void createPlayer(GameObject g)
    {
        g.addComponent(new PlayerController());
        g.addComponent(new Movement(4));
        g.addComponent(new BoxCollider());
    }


}
