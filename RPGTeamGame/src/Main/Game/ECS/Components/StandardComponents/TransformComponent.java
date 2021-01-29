package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.system.Vector2f;

public class TransformComponent extends Component
{
    private Vector2f size;
    private float rotation = 0;
    private Vector2f scale = new Vector2f(1,1);
    private boolean isFacingRight = false;

    public TransformComponent(Vector2f size)
    {
        this.size = size;
    }
    public TransformComponent(Vector2f size, float rotation)
    {
        this.size =size;
        this.rotation = rotation;
    }

    public Vector2f getSize() {
        return size;
    }

    public float getRotation() {
        return rotation;
    }
    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }
    public Vector2f getScale()
    {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }

    @Override
    public Component clone() {
        return new TransformComponent(size, rotation) ;
    }
}
