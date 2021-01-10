package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class XPBar extends Component
{
    private float maxXP;
    private float currentXP = maxXP;

    public XPBar(float maxXP)
    {
        this.maxXP = maxXP;
    }

    public float getCurrentXP() {
        return currentXP;
    }

    public float getMaxXP() {
        return maxXP;
    }

    @Override
    public Component clone() {
        return new XPBar(maxXP);
    }
}
