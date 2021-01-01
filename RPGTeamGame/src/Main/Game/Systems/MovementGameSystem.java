package Main.Game.Systems;

import Main.Game.Communication.GameEvent;
import Main.Game.Entity.Component;
import Main.Game.Entity.Components.Movement;
import Main.Game.Entity.Components.Position;
import Main.Game.Entity.Components.SpriteController;
import Main.Game.Level;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

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
            if (Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                ((Position) cA[0]).position = new Vector2f(((Position) cA[0]).position.x,((Position) cA[0]).position.y - ((Movement) cA[1]).speed);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                ((Position) cA[0]).position = new Vector2f(((Position) cA[0]).position.x - ((Movement) cA[1]).speed,((Position) cA[0]).position.y);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                ((Position) cA[0]).position = new Vector2f(((Position) cA[0]).position.x,((Position) cA[0]).position.y + ((Movement) cA[1]).speed);
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                ((Position) cA[0]).position = new Vector2f(((Position) cA[0]).position.x + ((Movement) cA[1]).speed,((Position) cA[0]).position.y);
            }

        }


    }

    @Override
    public void update(Event event)
    {

    }


}
