package Main.Game.Physics;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class CollisionBox
{
    RectangleShape box;
    public CollisionBox(Vector2f position)
    {
        box = new RectangleShape();
    }
}
