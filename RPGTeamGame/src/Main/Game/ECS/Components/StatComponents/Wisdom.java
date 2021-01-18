package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Entity.Component;

public class Wisdom extends Component implements IsStat
{
    float wisdom;
    float baseWisdom;
  public Wisdom(float wisdom)
  {
        this.wisdom = wisdom;
  }

    @Override
    public Component clone() {
        return new Wisdom(wisdom);
    }

    @Override
    public float getStat() {
        return wisdom;
    }

    @Override
    public void setStat(float value) {
        wisdom = value;
    }

    @Override
    public float getBase() {
        return baseWisdom;
    }
}
