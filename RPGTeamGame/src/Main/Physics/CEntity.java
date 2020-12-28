package Main.Physics;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class CEntity
{
    CollisionBox cBox;
    Vector2i centerPosition;
    float width, height;
    public CEntity(Vector2i position, float width, float height)
    {
        this.centerPosition = position;
        this.width = width;
        this.height = height;
        this.cBox = createCollisionBox();
    }

    public CollisionBox createCollisionBox() {
        Vector2f topLCorner = new Vector2f(centerPosition.x - width/2, centerPosition.y-height/2);
        Vector2f bottomRCorner = new Vector2f(centerPosition.x + width/2, centerPosition.y+height/2);
        return new CollisionBox(topLCorner,bottomRCorner);
    }

    public void updateMesh(Vector2i position)
    {
        centerPosition = position;
        Vector2f topLCorner = new Vector2f(centerPosition.x - width/2, centerPosition.y-height/2);
        Vector2f bottomRCorner = new Vector2f(centerPosition.x + width/2, centerPosition.y+height/2);
        cBox.setPosition(topLCorner,bottomRCorner);
    }

    public CollisionBox getcBox() {
        return cBox;
    }
}
