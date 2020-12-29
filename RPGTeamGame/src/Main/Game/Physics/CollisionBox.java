package Main.Game.Physics;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class CollisionBox
{
    FloatRect box;
    public CollisionBox(Vector2f position, Vector2f size)
    {
        box = new FloatRect(position,size);
    }
    public CollisionBox(FloatRect r)
    {
        box = r;
    }

    public FloatRect getBox() {
        return box;
    }
}
