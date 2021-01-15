package Main.DataTypes;

import Main.Game.ECS.Components.StatComponents.StatComponent;

public class Effects<T extends StatComponent>
{
    float percentageModifier;
    float duration;
    boolean hasDuration;

    public Effects(float percentageModifier)
    {
        this.percentageModifier = percentageModifier;
        this.hasDuration = false;
    }
    public Effects(float percentageModifier, float duration)
    {
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
        return duration >0;
    }

    public float getPercentageModifier() {
        return percentageModifier;
    }


}
