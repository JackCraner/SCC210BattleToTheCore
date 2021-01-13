package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class Stats extends Component
{
    float armor;
    float health;
    float damagemultipler;
    float healthRegeneration;           //could be placed with HealthBar component but chests and items wont have health regen so
    float manaRegeneration;

    @Override
    public Component clone() {
        return null;
    }
}
