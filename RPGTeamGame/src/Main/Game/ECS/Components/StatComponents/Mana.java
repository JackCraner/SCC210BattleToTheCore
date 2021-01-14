package Main.Game.ECS.Components.StatComponents;

public class Mana implements StatComponent
{
    private float currentMana;
    private float maxMana;

    public Mana(float mana)
    {
        this.currentMana = mana;
        this.maxMana = mana;
    }
    public float getCurrentMana() {
        return currentMana;
    }

    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void applyEffect(float percentage) {

    }
}
