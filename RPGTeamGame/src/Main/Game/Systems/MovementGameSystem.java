package Main.Game.Systems;

import Main.Game.Communication.GameEvent;
import Main.Game.Entity.Component;
import Main.Game.Entity.Components.Movement;
import Main.Game.Entity.Components.Position;
import Main.Game.Entity.Components.SpriteController;
import Main.Game.Level;
import org.jsfml.system.Vector2f;

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
            ((Position) cA[0]).position = new Vector2f(((Position) cA[0]).position.x + ((Movement) cA[1]).speed,((Position) cA[0]).position.y + ((Movement) cA[1]).speed);
        }


    }

    @Override
    public void update(GameEvent event) {

    }
}
