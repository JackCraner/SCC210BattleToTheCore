package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.BoxCollider;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.Position;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import javax.swing.*;
import java.util.ArrayList;

public class MovementGameSystem extends GameSystem
{
    private static MovementGameSystem systemInstance = new MovementGameSystem();

    public static MovementGameSystem getSystemInstance()
    {
        return systemInstance;
    }

    @Override
    public ArrayList<Class<? extends Component>> systemComponentRequirements() {
        ArrayList<Class<? extends Component>> c = new ArrayList<>();
        c.add(Position.class);
        c.add(Movement.class);
        return c;
    }

    @Override
    public void update()
    {

        for(Component[] cA: getComponentArrayList())
        {
            Vector2f curPos =  ((Position) cA[0]).position;
            if (Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                curPos = new Vector2f(curPos.x,curPos.y - ((Movement) cA[1]).speed);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                curPos = new Vector2f(curPos.x - ((Movement) cA[1]).speed,curPos.y);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                curPos = new Vector2f(curPos.x,curPos.y + ((Movement) cA[1]).speed);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                curPos = new Vector2f(curPos.x + ((Movement) cA[1]).speed,curPos.y);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.SPACE))
            {
                curPos = new Vector2f(curPos.x,curPos.y - 10);
            }

            curPos = new Vector2f(curPos.x , curPos.y + 1);
            ((Position) cA[0]).position = curPos;



        }

    }

    @Override
    public void update(Event event)
    {

    }


}
