package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class Damage extends Component
{
    float damage = 10;
    public Damage(float damage)
    {
        this.damage = damage;
    }
    public float getDamage()
    {
        return damage;
    }

}
