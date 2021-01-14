package Main.Game.ECS.Components;

import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Entity.Component;

public class Movement extends Component
{

    private Speed speed;                //speed in pixels per second
    private boolean isFacingRight = false;
    private MovementTypes type;
    public Movement(MovementTypes type, Speed speed)
    {
        this.type = type;
        this.speed = speed;
        isFacingRight = false;
    }
    public Movement(MovementTypes type, Speed speed, Boolean isFacingRight)
    {
        this.type = type;
        this.speed = speed;
        this.isFacingRight = isFacingRight;
    }

    public float getSpeed() {
        return speed.getSpeed();
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
