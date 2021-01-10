package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.EntityManager;
import org.jsfml.system.Vector2f;

public class Position extends Component
{

    private Vector2f position;
    public Position(Vector2f position)
    {
        this.position = position;
    }
    public void updatePosition(Vector2f position)
    {
        this.position = position;
    }

}
