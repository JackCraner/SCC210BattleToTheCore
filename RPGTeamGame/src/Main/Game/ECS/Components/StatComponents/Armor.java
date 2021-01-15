package Main.Game.ECS.Components.StatComponents;

public class Armor extends StatComponent
{
    public Armor(float armorValue)
    {
        //max armor is 100
        if (armorValue < 100)
        {
            setActiveValue(armorValue);
        }
        else
        {
            setActiveValue(100);
        }

    }

    @Override
    public float getStats() {
        float armorValue = super.getStats();
        return (1 - (armorValue/100));
    }
}
