package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.system.Vector2f;

public class Position extends Component
{

    public Vector2f position;
    public Position(Vector2f position)
    {
        this.position = position;
    }

}
