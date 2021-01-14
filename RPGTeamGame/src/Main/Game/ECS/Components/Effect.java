package Main.Game.ECS.Components;

import Main.Game.ECS.Components.StatComponents.StatComponent;
import Main.Game.ECS.Entity.Component;

public class Effect extends Component
{
    private final Class<? extends StatComponent> effectType;
    private boolean hasDuration;
    private float durationTotal;
    private float durationCurrent;
    private float effectStrength;    //as percentage

    public Effect(Class<? extends StatComponent> effectType, float effectStrength)
    {
        this.effectType = effectType;
        this.effectStrength = effectStrength;
        hasDuration = false;
    }
    public Effect(Class<? extends StatComponent> effectType, float duration, float effectStrength)
    {
        this.effectType = effectType;
        this.durationTotal = duration;
        this.durationCurrent =0;
        hasDuration = true;
        this.effectStrength = effectStrength;
    }

    public Class<? extends StatComponent> getEffectType() {
        return effectType;
    }

    public float getDurationTotal() {
        return durationTotal;
    }

    public float getDurationCurrent() {
        return durationCurrent;
    }

    public void setDurationCurrent(float durationCurrent) {
        this.durationCurrent = durationCurrent;
    }

    public boolean noDuration()
    {
        return !hasDuration;
    }

    public float getEffectStrength() {
        return effectStrength;
    }

    @Override
    public Component clone() {
        return null;
    }
}
