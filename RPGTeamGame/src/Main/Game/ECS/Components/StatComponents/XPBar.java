package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

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
