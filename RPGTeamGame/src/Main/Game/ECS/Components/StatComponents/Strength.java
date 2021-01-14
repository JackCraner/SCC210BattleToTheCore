package Main.Game.ECS.Components.StatComponents;

public class Strength  extends StatComponent
{
    private float strength;

    public Strength(float strength)
    {
        this.strength = strength;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }
}
