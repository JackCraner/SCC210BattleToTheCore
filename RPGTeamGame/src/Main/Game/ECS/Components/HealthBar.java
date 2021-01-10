package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class HealthBar extends Component
{
    private float maxHealth;
    private float currentHealth;

    public HealthBar(float maxHealth)
    {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void adjustHealth(float x)
    {
        currentHealth +=x;
    }
}
