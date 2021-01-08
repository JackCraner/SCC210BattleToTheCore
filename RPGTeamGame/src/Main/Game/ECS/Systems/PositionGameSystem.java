package Main.Game.ECS.Systems;

import org.jsfml.window.event.Event;

public class PositionGameSystem extends GameSystem
{
    private static PositionGameSystem positionGameSystem = new PositionGameSystem();

    public static PositionGameSystem getPositionGameSystem() {
        return positionGameSystem;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(Event event) {

    }
}
