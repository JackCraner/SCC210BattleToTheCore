package Main.Game.ECS.Components.StatComponents;

public class Armor extends StatComponent
{
    private float armorValue;

    public Armor(float armorValue)
    {
        //max armor is 100
        if (armorValue < 100)
        {
            this.armorValue = armorValue;
        }
        else
        {
            this.armorValue = 100;
        }

    }

    public float getArmorValue() {
        return (1-(armorValue/100));
    }

    public void setArmorValue(float armorValue) {
        this.armorValue = armorValue;
    }
}
