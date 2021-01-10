package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.LifeSpan;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

import java.util.ArrayList;

public class LifeSpanGameSystem extends GameSystem
{
    private static LifeSpanGameSystem systemInstance = new LifeSpanGameSystem();

    public static LifeSpanGameSystem getSystemInstance() {
        return systemInstance;
    }
    private LifeSpanGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(LifeSpan.class));
    }
    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {
        for(GameObject g: getGameObjectList())
        {
            LifeSpan l = g.getComponent(LifeSpan.class);

            if (l.checkIfFinished())
            {
                EntityManager.getEntityManagerInstance().removeGameObject(g);
            }
            else
            {
                l.countLifeSpan();
            }
        }
    }
}
