package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.Game;

public class EventClearGameSystem extends GameSystem
{
    private static EventClearGameSystem systemInstance = new EventClearGameSystem();

    public static EventClearGameSystem getSystemInstance() {
        return systemInstance;
    }
    private EventClearGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(CollisionEvent.class));
    }


    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            if(g.getName() == Entity.PLAYER.name)
            {
                if (g.getComponent(Inputs.class).pickUP && g.getComponent(CollisionEvent.class).getG().getName() == Entity.TRAPDOOR.name)
                {
                    Game.getGame().newMap();
                }
            }
            g.removeComponent(CollisionEvent.class);
        }

    }
}
