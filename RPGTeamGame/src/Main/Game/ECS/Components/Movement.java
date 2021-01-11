package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class Movement extends Component
{

    private float speed = 1;
    private MovementTYPES type;
    public Movement(MovementTYPES type, float speed)
    {
        this.type = type;
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float x)
    {
        speed = x;
    }

    public MovementTYPES getType() {
        return type;
    }

    @Override
    public Component clone() {
        return new Movement(type, speed);
    }
}
