package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.CollisionEvent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

import java.util.ArrayList;

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
    public void update(ArrayList<GameEvent> gameEvents)
    {
        for (GameObject g: getGameObjectList())
        {
            g.removeComponent(CollisionEvent.class);
        }

    }
}
