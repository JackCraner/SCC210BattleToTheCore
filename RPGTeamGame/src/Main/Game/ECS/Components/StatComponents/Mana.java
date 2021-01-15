package Main.Game.ECS.Components.StatComponents;

public class Mana extends StatComponent
{
    public Mana(float mana)
    {
        setActiveValue(mana);
        setMaxValue(mana);
    }
    public void adjustMana(float x)
    {
        setActiveValue(getActiveValue() +x);
    }
    public float getMaxMana()
    {
        return getMaxValue();
    }
}
