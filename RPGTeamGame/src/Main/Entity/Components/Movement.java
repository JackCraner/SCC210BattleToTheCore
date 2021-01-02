package Main.Entity.Components;

import Main.Entity.Component;
import org.jsfml.system.Vector2f;

public class Movement extends Component
{
    Vector2f velocity = new Vector2f(0,0);
    float speed = 1;
    public Movement(float speed)
    {
        this.speed = speed;
    }
    public void move(Vector2f movementForce)
    {
        velocity = new Vector2f(movementForce.x * speed, movementForce.y * speed);
    }

    @Override
    public void update(float dt)
    {
        getGameObject().setPosition(new Vector2f(getGameObject().getPosition().x + velocity.x,getGameObject().getPosition().y + velocity.y ));
    }





}
