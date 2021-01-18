package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Entity.Component;


public class Armor extends Component implements IsStat {
    float baseArmor;
    float armorValue;

    public Armor(float armorValue) {

        //max armor is 100
        if (armorValue < 100) {
            this.armorValue = armorValue;
            this.baseArmor = armorValue;
        } else {
            this.armorValue = 100;
            this.baseArmor = 100;
        }

    }

    @Override
    public float getStat() {
        return (1 - (armorValue / 100));
    }

    @Override
    public void setStat(float value) {
        armorValue = value;
    }

    @Override
    public float getBase() {
        return baseArmor;
    }

    @Override
    public Component clone() {
        return new Armor(armorValue);
    }

}
