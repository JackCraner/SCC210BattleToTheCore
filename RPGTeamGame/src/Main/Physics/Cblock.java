package Main.Physics;

import org.jsfml.system.Vector2f;

public class Cblock implements Collidable
{
    private CollisionBox cBox;
    private Vector2f centerPosition;

    private float size;
    private boolean isCollidable;

    public Cblock(Vector2f position, boolean isCollidable)
    {
        this.centerPosition = position;
        this.size = 1;
        this.cBox = createCollisionBox();
        this.isCollidable = isCollidable;
    }

    public CollisionBox createCollisionBox() {
        Vector2f topLCorner = new Vector2f(centerPosition.x - size/2, centerPosition.y  - size/2);
        Vector2f bottomRCorner = new Vector2f(centerPosition.x  + size/2, centerPosition.y + size/2);
        return new CollisionBox(topLCorner,bottomRCorner);
    }

    public CollisionBox getcBox() {
        return cBox;
    }

    public boolean getisCollidable(){
        return isCollidable;
    }
}
