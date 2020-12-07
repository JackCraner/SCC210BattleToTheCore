package Main.Physics;

import org.jsfml.system.Vector2f;

public class CEntity
{
    CollisionBox cBox;
    Vector2f centerPosition;
    float width, height;
    public CEntity(Vector2f position, float width, float height)
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

    public void updateMesh(Vector2f position)
    {
        centerPosition = position;
        Vector2f topLCorner = new Vector2f(centerPosition.x - width/2, centerPosition.y-height/2);
        Vector2f bottomRCorner = new Vector2f(centerPosition.x + width/2, centerPosition.y+height/2);
        cBox.setPosition(topLCorner,bottomRCorner);
    }
}
