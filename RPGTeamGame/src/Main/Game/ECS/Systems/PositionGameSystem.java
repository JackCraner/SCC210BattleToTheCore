package Main.Game.ECS.Systems;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Components.Movement;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public class PositionGameSystem extends GameSystem
{
    private static PositionGameSystem systemInstance = new PositionGameSystem();



    @Override
    public ArrayList<Class<? extends Component>> systemComponentRequirements()
    {
       ArrayList<Class<? extends Component>> c = new ArrayList<>();
       c.add(Movement.class);
       return c;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(Event event) {

    }


    public static PositionGameSystem getSystemInstance() {
        return systemInstance;
    }




}
