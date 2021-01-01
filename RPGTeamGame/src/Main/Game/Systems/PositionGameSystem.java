package Main.Game.Systems;

import Main.Game.Communication.GameEvent;
import Main.Game.Entity.Component;
import Main.Game.Entity.Components.Movement;

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
    public void update(GameEvent event) {

    }


    public static PositionGameSystem getSystemInstance() {
        return systemInstance;
    }




}
