package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.CollisionEvent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

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
            g.removeComponent(CollisionEvent.class);
        }

    }
}
