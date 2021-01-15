package Main.Game.ECS.Components.StatComponents;

public class Health extends StatComponent {


    public Health(float health)
    {
        setActiveValue(health);
        setMaxValue(health);
    }
    public void adjustHealth(float x)
    {
        setActiveValue(getActiveValue() + x);
    }
    public float getMaxHealth()
    {
        return getMaxValue();
    }
}
