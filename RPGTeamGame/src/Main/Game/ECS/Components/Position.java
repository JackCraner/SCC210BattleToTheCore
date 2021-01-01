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
    public Position(float v1,float v2)
    {
        this.position = new Vector2f(v1,v2);
    }
    public Position()
    {
        this.position = new Vector2f(0,0);
    }
}
