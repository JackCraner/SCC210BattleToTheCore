package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class LifeSpan extends Component
{
    private float lifespanTotal;
    private float currentLifeSpan =0;
    public LifeSpan(float lifespanTotal)
    {
        this.lifespanTotal = lifespanTotal;
    }
    public Boolean checkIfFinished()
    {
        return (lifespanTotal == currentLifeSpan);
    }
    public void countLifeSpan()
    {
        if (lifespanTotal > currentLifeSpan)
        {
            currentLifeSpan ++;
        }
    }
    @Override
    public Component clone() {
        return new LifeSpan(lifespanTotal);
    }
}
