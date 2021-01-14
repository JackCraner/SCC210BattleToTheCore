package Main.Game.ECS.Components.StatComponents;

public class Health extends StatComponent
{
    private float currentHealth;
    private float maxHealth;

    public Health(float health)
    {
        currentHealth = health;
        maxHealth = health;
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
