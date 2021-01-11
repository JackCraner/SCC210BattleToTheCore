package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.MovementTYPES;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.TransformComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

import java.util.ArrayList;

/**
 * MOVEMENTGAMESYSTEM
 *
 * - handles movement of GameObjects
 *
 */
public class MovementGameSystem extends GameSystem
{
    private static MovementGameSystem systemInstance = new MovementGameSystem();

    public static MovementGameSystem getSystemInstance()
    {
        return systemInstance;
    }

    /**
     * SETS BITMASK
     */
    private MovementGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class,Movement.class));
    }

    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {

        for(GameObject g: getGameObjectList())
        {
            Vector2f curPos =  g.getComponent(Position.class).getPosition();
            Movement movement = g.getComponent(Movement.class);
            Vector2f newPos = curPos;
            if(movement.getType() == MovementTYPES.CONTROLLED)
            {
                if (Keyboard.isKeyPressed(Keyboard.Key.W))
                {
                    newPos = new Vector2f(newPos.x,newPos.y -  movement.getSpeed());
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.A))
                {
                    newPos = new Vector2f(newPos.x - movement.getSpeed() ,newPos.y);
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.S))
                {
                    newPos = new Vector2f(newPos.x,newPos.y +  movement.getSpeed() );
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.D))
                {
                    newPos = new Vector2f(newPos.x +  movement.getSpeed() ,newPos.y);
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.SPACE))
                {
                    newPos = new Vector2f(newPos.x,newPos.y - 1);
                }


            }
            if (movement.getType() == MovementTYPES.LINEAR)
            {

                if ((g.getBitmask() & BitMasks.produceBitMask(TransformComponent.class)) !=0)
                {
                    TransformComponent t = g.getComponent(TransformComponent.class);
                    newPos = new Vector2f(curPos.x + (float)Math.cos(Math.toRadians(t.getRotation())) * movement.getSpeed(), curPos.y + (float)Math.sin(Math.toRadians(t.getRotation())) * movement.getSpeed());
                }

            }

            if (newPos != curPos)
            {
                g.getComponent(Position.class).updatePosition(newPos);
            }



        }

    }



}
