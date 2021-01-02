package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.system.Vector2f;

public class Size extends Component
{
    public Vector2f size;

    public Size(Vector2f size)
    {
        this.size = size;
    }
    public Size(float size)
    {
        this.size = new Vector2f(size,size);
    }



}
