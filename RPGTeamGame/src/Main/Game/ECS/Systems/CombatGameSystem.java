package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIComponentENUM;
import Main.Game.GUI.GUIManager;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class CombatGameSystem extends GameSystem
{
    private static CombatGameSystem systemInstance = new CombatGameSystem();
    public static CombatGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private CombatGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(HealthBar.class));
    }
    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            HealthBar hp = g.getComponent(HealthBar.class);
            if ((g.getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) != 0)
            {
                GameObject hitBY = g.getComponent(CollisionEvent.class).getG();

                if ((hitBY.getBitmask() & BitMasks.produceBitMask(Damage.class)) != 0)
                {
                    g.removeComponent(CollisionEvent.class);
                    hp.adjustHealth(-hitBY.getComponent(Damage.class).getDamage());
                    //hit numbers
                    Vector2f textPos = g.getComponent(Position.class).getPosition();
                    textPos = new Vector2f(textPos.x + new Random().nextInt(30)-15, textPos.y + 20);
                    EntityManager.getEntityManagerInstance().addGameObject(Blueprint.damageNumber(textPos,hitBY.getComponent(Damage.class).getDamage()));
                    //
                    hitBY.removeComponent(Damage.class);
                    if (g.getName() == Entity.PLAYER.name)
                    {
                        GUIManager.getGUIinstance().GUIUpdate(GUIComponentENUM.HEALTHBAR);
                    }
                }
            }
            if (hp.getCurrentHealth() <= 0)
            {
                EntityManager.getEntityManagerInstance().removeGameObject(g);
                if ((g.getBitmask() & BitMasks.getBitMask(Backpack.class)) != 0)
                {
                    Backpack b = g.getComponent(Backpack.class);
                    Vector2f pos = g.getComponent(Position.class).getPosition();
                    Vector2f size = g.getComponent(TransformComponent.class).getSize();
                    pos = new Vector2f(pos.x + size.x/2, pos.y + size.y/2);
                    for (GameObject g1: b.getObjectsINBACKPACK())
                    {
                        g1.addComponent(new Position(pos,g1));
                        EntityManager.getEntityManagerInstance().addGameObject(g1);
                    }
                }
            }

        }
    }
}