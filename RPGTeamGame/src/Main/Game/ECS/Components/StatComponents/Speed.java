package Main.Game.ECS.Components.StatComponents;

public class Speed implements StatComponent
{
    float speed;
    public Speed(float speed)
    {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void applyEffect(float percentage)
    {
        speed *= percentage;
    }
}
