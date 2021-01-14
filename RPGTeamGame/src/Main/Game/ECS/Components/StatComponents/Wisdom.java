package Main.Game.ECS.Components.StatComponents;

public class Wisdom implements StatComponent
{
    private float wisdom;

    public Wisdom(float wisdom)
    {
        this.wisdom = wisdom;
    }

    public float getWisdom() {
        return wisdom;
    }

    public void setWisdom(float wisdom) {
        this.wisdom = wisdom;
    }

    @Override
    public void applyEffect(float percentage) {

    }
}
