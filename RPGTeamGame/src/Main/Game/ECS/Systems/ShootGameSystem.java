package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.Shoot;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

public class ShootGameSystem extends GameSystem
{

    private static ShootGameSystem shootGameSystem = new ShootGameSystem();

    public static ShootGameSystem getShootGameSystem()
    {
        return shootGameSystem;
    }

    private ShootGameSystem()
    {
        setBitMaskRequirement(EntityManager.getEntityManagerInstance().produceBitMask(Shoot.class));
    }

    @Override
    public void update()
    {
        for (GameObject g :getGameObjectList())
        {

            Shoot s = g.getComponent(Shoot.class);


            //logic


           //   int speed = g.getComponent(Shoot.class).speed;

            if(Keyboard.isKeyPressed(Keyboard.Key.SPACE))
            {

                //Create a bullet game object and add it to entityManager
                //EntityManager.getEntityManagerInstance().addGameObject(Blueprint.player(new Vector2f(Game.PLAYER.getPosition().x + 20,Game.PLAYER.getPosition().y + 20)));
                //System.out.println(g.getName());

            }

        }
    }

    @Override
    public void update(Event event) {

    }
}
