package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.Pickup;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIComponentENUM;
import Main.Game.GUI.GUIManager;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;

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
                        if (backpack.inventoryHasSpace() && Mouse.isButtonPressed(Mouse.Button.LEFT))   //Either remove pickup button or make item hitbox bigger
                        {
                            EntityManager.getEntityManagerInstance().removeGameObject(pickupObject);
                            pickupObject.removeComponent(Position.class);
                            pickupObject.removeComponent(CollisionEvent.class);
                            pickupObject.getComponent(Pickup.class).attach(g);
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
                    GameObject mainHand = backpack.getObjectsINBACKPACK().get(0);
                    Pickup mainHandEffect = mainHand.getComponent(Pickup.class);
                    if(mainHandEffect.doesSpawn())
                    {
                        Vector2f pos = g.getComponent(Position.class).getPosition();
                        Vector2i mousePos = Mouse.getPosition(Game.getGame().getWindow());
                        Vector2f camPos = new Vector2f(Camera.cameraInstance().camerView.getCenter().x - (Camera.cameraInstance().camerView.getSize().x/2),Camera.cameraInstance().camerView.getCenter().y - (Camera.cameraInstance().camerView.getSize().y/2));
                        //find angle
                        mousePos = new Vector2i((int)camPos.x + mousePos.x, (int)camPos.y + mousePos.y);
                        float angle =   (float)((Math.atan2(mousePos.y - pos.y, mousePos.x- pos.x))*180/Math.PI);
                        mainHand.getComponent(TransformComponent.class).setRotation(angle);
                        if(mainHandEffect.isReady() && Mouse.isButtonPressed(Mouse.Button.RIGHT))
                        {
                            pos = new Vector2f(pos.x + (float)(Math.cos(Math.toRadians(angle)) * Blueprint.OBJECTSIZE.x), pos.y + (float)(Math.sin(Math.toRadians(angle)) * Blueprint.OBJECTSIZE.y));
                            GameObject spawn = mainHandEffect.getSpawns(pos);
                            spawn.getComponent(TransformComponent.class).setRotation(angle);
                            EntityManager.getEntityManagerInstance().addGameObject(spawn);
                        }
                    }


                    if(Keyboard.isKeyPressed(Keyboard.Key.E) && backpack.getEmptyCooldown() == 0)
                    {
                        backpack.getObjectsINBACKPACK().remove(0);
                        backpack.setEmptyCooldown(200);
                        mainHand.addComponent(new Position(g.getComponent(Position.class).getPosition(),mainHand));
                        mainHand.getComponent(Collider.class).setAvoidTime(g,500);
                        EntityManager.getEntityManagerInstance().addGameObject(mainHand);
                        if (g.getName() == Entity.PLAYER.name)
                        {
                            GUIManager.getGUIinstance().GUIUpdate(GUIComponentENUM.INVENTORY);
                        }
                    }
                    mainHandEffect.reduceCoolDown();
                }
                if (backpack.getEmptyCooldown() >0)
                {
                    backpack.setEmptyCooldown(backpack.getEmptyCooldown() -1);
                }


           }
    }
}
