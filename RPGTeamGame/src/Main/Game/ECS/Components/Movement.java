package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class Movement extends Component
{

    private float speed = 1;
    private boolean isFacingRight = false;
    private MovementTYPES type;
    public Movement(MovementTYPES type, float speed)
    {
        this.type = type;
        this.speed = speed;
        isFacingRight = false;
    }
    public Movement(MovementTYPES type, float speed, Boolean isFacingRight)
    {
        this.type = type;
        this.speed = speed;
        this.isFacingRight = isFacingRight;
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float x)
    {
        speed = x;
    }
    public Boolean getIsFacingRight()
    {
        return isFacingRight;
    }
    public void setIsFacingRight(Boolean b)
    {
        isFacingRight = b;
    }
    public MovementTYPES getType() {
        return type;
    }

    @Override
    public Component clone() {
        return new Movement(type, speed);
    }
}
