package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.Pickup;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIComponentENUM;
import Main.Game.GUI.GUIManager;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

import java.util.ArrayList;

public class BackpackGameSystem extends GameSystem
{

    private static BackpackGameSystem systemInstance = new BackpackGameSystem();
    public static BackpackGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private BackpackGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Backpack.class));
    }
    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {

           for (GameObject g: getGameObjectList())
           {
                Backpack backpack = g.getComponent(Backpack.class);
                if ((g.getBitmask()  & BitMasks.produceBitMask(CollisionEvent.class) )!=0)
                {
                    GameObject pickupObject = g.getComponent(CollisionEvent.class).getG();

                    if ((pickupObject.getBitmask() & BitMasks.produceBitMask(Pickup.class)) != 0)
                    {
                        g.removeComponent(CollisionEvent.class);
                        if (backpack.inventoryHasSpace())
                        {
                            EntityManager.getEntityManagerInstance().removeGameObject(pickupObject);
                            pickupObject.removeComponent(Position.class);
                            backpack.addGameObject(pickupObject);
                            if (g.getName() == Entity.PLAYER.name)
                            {
                                GUIManager.getGUIinstance().GUIUpdate(GUIComponentENUM.INVENTORY);
                            }
                        }

                    }
                }
                if (backpack.getObjectsINBACKPACK().size() > 0)
                {
                    Pickup mainHand = backpack.getObjectsINBACKPACK().get(0).getComponent(Pickup.class);
                    if (Keyboard.isKeyPressed(Keyboard.Key.E))
                    {

                        if(mainHand.isReady())
                        {
                            Vector2f pos = g.getComponent(Position.class).getPosition();
                            EntityManager.getEntityManagerInstance().addGameObject(mainHand.getSpawns(new Vector2f(pos.x+32, pos.y -32)));
                        }

                    }
                    mainHand.reduceCoolDown();
                }


           }
    }
}
