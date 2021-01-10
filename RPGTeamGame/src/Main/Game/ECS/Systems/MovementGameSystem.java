package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.Game;
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
            Vector2f curPos =  g.getComponent(Position.class).position;
            float speed = g.getComponent(Movement.class).speed;

            if (Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                curPos = new Vector2f(curPos.x,curPos.y -  speed );
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                curPos = new Vector2f(curPos.x - speed ,curPos.y);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                curPos = new Vector2f(curPos.x,curPos.y +  speed );
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                curPos = new Vector2f(curPos.x +  speed ,curPos.y);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.SPACE))
            {
                curPos = new Vector2f(curPos.x,curPos.y - 1);
            }
            Game.ENTITYMANAGER.updateLeaf(g,curPos);
            g.getComponent(Position.class).position = curPos;



        }

    }



}
