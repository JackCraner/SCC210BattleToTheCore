package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;


public class Armor extends Component implements IsStat {
    float baseArmor;
    float armorValue;
    GameObject helmet;
    GameObject chest;
    GameObject legs;

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

    public GameObject getHelmet() {
        return helmet;
    }

    public void setHelmet(GameObject helmet) {
        this.helmet = helmet;
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
