package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIHealthBar;
import Main.Game.Managers.GUIManager;
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
        setBitMaskRequirement(BitMasks.produceBitMask(Health.class));
    }
    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList()) {
            Health objectHealth = g.getComponent(Health.class);
            if ((g.getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) != 0) {
                GameObject hitBY = g.getComponent(CollisionEvent.class).getG();
                if (!(hitBY.getComponent(Collider.class).checkGameObject(g))) {
                    if ((hitBY.getBitmask() & BitMasks.produceBitMask(Damage.class)) != 0) {
                        float damageDealt;
                        if (BitMasks.checkIfContains(g.getBitmask(), Armor.class)) {
                            damageDealt = (hitBY.getComponent(Damage.class).getDamage() * g.getComponent(Armor.class).getStat());
                        } else {
                            damageDealt = hitBY.getComponent(Damage.class).getDamage();

                        }

                        //hit numbers
                        objectHealth.adjustHealth(-damageDealt);
                        Vector2f textPos = g.getComponent(Position.class).getPosition();
                        textPos = new Vector2f(textPos.x + new Random().nextInt(30) - 15, textPos.y + 20);
                        EntityManager.getEntityManagerInstance().addGameObject(Blueprint.damageNumber(textPos, damageDealt));
                        hitBY.getComponent(Collider.class).setAvoidTime(g);
                        if (g.getName() == Entity.PLAYER.name) {
                            GUIManager.getGUIinstance().GUIUpdate(GUIHealthBar.class);
                        }
                    }
                }

            }
            if (objectHealth.getStat() <= 0) {
                EntityManager.getEntityManagerInstance().removeGameObject(g);
                if ((g.getBitmask() & BitMasks.getBitMask(Backpack.class)) != 0) {
                    Backpack b = g.getComponent(Backpack.class);
                    Vector2f pos = g.getComponent(Position.class).getPosition();
                    Vector2f size = g.getComponent(TransformComponent.class).getSize();
                    pos = new Vector2f(pos.x + size.x / 2, pos.y + size.y / 2);
                    for (GameObject g1 : b.getObjectsINBACKPACK()) {
                        g1.addComponent(new Position(pos, g1));
                        EntityManager.getEntityManagerInstance().addGameObject(g1);
                    }
                }
                if (BitMasks.checkIfContains(g.getBitmask(), Level.class)) {
                    /*
                    Level objectLevel = g.getComponent(Level.class);
                    Position objectPosition = g.getComponent(Position.class);
                    for (int i = 0; i < objectLevel.getCurrentLevel(); i++)
                    {
                        EntityManager.getEntityManagerInstance().addGameObject(Blueprint.xpOrb(objectPosition.getPosition()));
                    }

                     */
                }
            }
        }
    }
}
