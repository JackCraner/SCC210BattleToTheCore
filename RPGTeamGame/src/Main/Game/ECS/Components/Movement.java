package Main.Game.ECS.Components;

import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Entity.Component;

public class Movement extends Component
{

    private float speed = 1;                //speed in pixels per second
    private boolean isFacingRight = false;
    private MovementTypes type;
    public Movement(MovementTypes type, float speed)
    {
        this.type = type;
        this.speed = speed;
        isFacingRight = false;
    }
    public Movement(MovementTypes type, float speed, Boolean isFacingRight)
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
    public MovementTypes getType() {
        return type;
    }

    @Override
    public Component clone() {
        return new Movement(type, speed);
    }
}
