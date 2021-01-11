package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.system.Vector2f;

public class TransformComponent extends Component
{
    private Vector2f size;
    private float rotation = 0;


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

    @Override
    public Component clone() {
        return new TransformComponent(size, rotation) ;
    }
}
