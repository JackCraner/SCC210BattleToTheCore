package Main.Physics;

import org.jsfml.system.Vector2f;

public class Cblock implements Collidable
{
    CollisionBox cBox;
    Vector2f centerPosition;
    float size;
    public Cblock(Vector2f position, float size)
    {
        this.centerPosition = position;
        this.size = size;
        this.cBox = createCollisionBox();
    }

    public CollisionBox createCollisionBox() {
        Vector2f topLCorner = new Vector2f(centerPosition.x - size/2, centerPosition.y-size/2);
        Vector2f bottomRCorner = new Vector2f(centerPosition.x + size/2, centerPosition.y+size/2);
        return new CollisionBox(topLCorner,bottomRCorner);
    }
}
