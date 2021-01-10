package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIComponentENUM;
import Main.Game.GUI.GUIManager;

import java.util.ArrayList;

public class CombatGameSystem extends GameSystem
{
    private static CombatGameSystem systemInstance = new CombatGameSystem();
    public static CombatGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private CombatGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(CollisionEvent.class, HealthBar.class));
    }
    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {
        for (GameObject g: getGameObjectList())
        {
            HealthBar hp = g.getComponent(HealthBar.class);
            GameObject hitBY = g.getComponent(CollisionEvent.class).getG();

            if ((hitBY.getBitmask() & BitMasks.produceBitMask(Damage.class)) != 0)
            {
                g.removeComponent(CollisionEvent.class);
                hp.adjustHealth(-hitBY.getComponent(Damage.class).getDamage());
                EntityManager.getEntityManagerInstance().removeGameObject(hitBY);
                if (g.getName() == Entity.PLAYER.name)
                {
                    GUIManager.getGUIinstance().GUIUpdate(GUIComponentENUM.HEALTHBAR);
                }
            }
        }
    }
}
