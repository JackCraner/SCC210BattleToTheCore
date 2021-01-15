package Main.DataTypes;

import Main.Game.ECS.Components.Effect;
import Main.Game.ECS.Components.StatComponents.StatComponent;

public class Effects
{
    Class<? extends StatComponent> effectType;
    float percentageModifier;
    float duration;
    boolean hasDuration;

    public Effects(Class<? extends StatComponent> effectType, float percentageModifier)
    {
        this.effectType = effectType;
        this.percentageModifier = percentageModifier;
        this.hasDuration = false;
    }
    public Effects(Class<? extends StatComponent> effectType, float percentageModifier, float duration)
    {
        this.effectType = effectType;
        this.percentageModifier = percentageModifier;
        this.duration = duration;
        this.hasDuration = true;
    }

    public void effectClock(float dt)
    {
        if (hasDuration)
        {
            duration -=dt;
        }
    }
    public boolean effectActive()
    {
        return ((hasDuration && duration<0) || !hasDuration);
    }

    public float getPercentageModifier() {
        return percentageModifier;
    }

    public Class<? extends StatComponent> getEffectType() {
        return effectType;
    }
}
