package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.MovementTYPES;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.TransformComponent;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.Game;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

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
    public void update(float dt)
    {
        Vector2f curPos;
        Movement movement;
        Vector2f newPos;
        float speed;
        for(GameObject g: getGameObjectList())
        {
            curPos =  g.getComponent(Position.class).getPosition();
            movement = g.getComponent(Movement.class);
            newPos = curPos;
            speed = movement.getSpeed() * dt;
            if(movement.getType() == MovementTYPES.CONTROLLED)
            {
                if (Keyboard.isKeyPressed(Keyboard.Key.W))
                {
                    newPos = new Vector2f(newPos.x,newPos.y -  speed);
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.A))
                {
                    newPos = new Vector2f(newPos.x - speed ,newPos.y);
                    movement.setIsFacingRight(false);
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.S))
                {
                    newPos = new Vector2f(newPos.x,newPos.y +  speed );
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.D))
                {
                    newPos = new Vector2f(newPos.x +  speed ,newPos.y);
                    movement.setIsFacingRight(true);
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
                    newPos = new Vector2f(curPos.x + (float)Math.cos(Math.toRadians(t.getRotation())) * speed, curPos.y + (float)Math.sin(Math.toRadians(t.getRotation())) * speed);
                }

            }

            if (newPos != curPos)
            {
                g.getComponent(Position.class).updatePosition(newPos);

            }



        }

    }



}
