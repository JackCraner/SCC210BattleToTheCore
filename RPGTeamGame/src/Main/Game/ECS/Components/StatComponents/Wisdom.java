package Main.Game.ECS.Components.StatComponents;

public class Wisdom extends StatComponent
{
  public Wisdom(float wisdom)
  {
      setActiveValue(wisdom);
      setMaxValue(wisdom);
  }
}
