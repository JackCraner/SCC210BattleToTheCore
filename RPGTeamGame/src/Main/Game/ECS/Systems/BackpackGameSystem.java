package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Entity.Camera;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIInvectory;
import Main.Game.Managers.GUIManager;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;

public class BackpackGameSystem extends GameSystem
{

    private static BackpackGameSystem systemInstance = new BackpackGameSystem();
    public static BackpackGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private BackpackGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Backpack.class, Inputs.class));
    }
    @Override
    public void update(float dt)
    {

           for (GameObject g: getGameObjectList())
           {
                Backpack backpack = g.getComponent(Backpack.class);
                Inputs oInputs = g.getComponent(Inputs.class);
                if ((g.getBitmask()  & BitMasks.getBitMask(CollisionEvent.class))!=0)
                {
                    GameObject pickupObject = g.getComponent(CollisionEvent.class).getG();
                    if ((pickupObject.getBitmask() & BitMasks.getBitMask(Pickup.class)) != 0)
                    {
                        g.removeComponent(CollisionEvent.class);
                        if (backpack.inventoryHasSpace() &&oInputs.pickUP)   //Either remove pickup button or make item hitbox bigger
                        {
                            EntityManager.getEntityManagerInstance().removeGameObject(pickupObject);
                            pickupObject.removeComponent(Position.class);
                            pickupObject.getComponent(Pickup.class).attach(g);
                            backpack.addGameObject(pickupObject);
                            if (g.getName() == Entity.PLAYER.name)
                            {
                                GUIManager.getGUIinstance().GUIUpdate(GUIInvectory.class);
                            }
                        }

                    }
                }
                if (backpack.getObjectsINBACKPACK().size() > 0)
                {
                    GameObject mainHand = backpack.getObjectsINBACKPACK().get(0);
                    if (backpack.getCanUseItems())
                    {
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
                            if(mainHandEffect.isReady() && oInputs.use)
                            {
                                pos = new Vector2f(pos.x + (float)(Math.cos(Math.toRadians(angle)) * Blueprint.OBJECTSIZE.x), pos.y + (float)(Math.sin(Math.toRadians(angle)) * Blueprint.OBJECTSIZE.y));
                                GameObject spawn = mainHandEffect.getSpawns(pos);
                                spawn.getComponent(TransformComponent.class).setRotation(angle);
                                EntityManager.getEntityManagerInstance().addGameObject(spawn);
                            }
                        }
                        mainHandEffect.reduceCoolDown(dt);
                    }



                    if(oInputs.drop && backpack.getEmptyCooldown() <= 0 && backpack.getCanDropItems())
                    {
                        backpack.getObjectsINBACKPACK().remove(0);
                        backpack.setEmptyCooldown(0.4f);
                        mainHand.addComponent(new Position(g.getComponent(Position.class).getPosition(),mainHand));
                        mainHand.getComponent(Collider.class).setAvoidTime(g,1.5f);
                        EntityManager.getEntityManagerInstance().addGameObject(mainHand);
                        if (g.getName() == Entity.PLAYER.name)
                        {
                            GUIManager.getGUIinstance().GUIUpdate(GUIInvectory.class);
                        }
                    }

                }
                if (backpack.getEmptyCooldown() >0)
                {
                    backpack.setEmptyCooldown(backpack.getEmptyCooldown() - dt);
                }


           }
    }
}
