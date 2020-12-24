package Main.Physics;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Cblock implements Collidable
{
    CollisionBox cBox;
    Vector2i centerPosition;
    float size;
    public Cblock(Vector2i position)
    {
        this.centerPosition = position;
        this.size = 16;
        this.cBox = createCollisionBox();
    }

    public CollisionBox createCollisionBox() {
        Vector2f topLCorner = new Vector2f(centerPosition.x - size/2, centerPosition.y - size/2);
        Vector2f bottomRCorner = new Vector2f(centerPosition.x + size/2, centerPosition.y + size/2);
        return new CollisionBox(topLCorner,bottomRCorner);
    }

    public CollisionBox getcBox() {
        return cBox;
    }
}
