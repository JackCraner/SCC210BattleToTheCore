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
    public HealthBar(float maxHealth, float currentHealth)
    {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
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

    @Override
    public Component clone() {
        return new HealthBar(maxHealth,currentHealth);
    }
}
