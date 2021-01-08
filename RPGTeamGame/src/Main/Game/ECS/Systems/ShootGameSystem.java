package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.Shoot;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
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
        setBitMaskRequirement(BitMasks.produceBitMask(Shoot.class));
    }

    @Override
    public void update()
    {

    }

    @Override
    public void update(Event event) {

    }
}
