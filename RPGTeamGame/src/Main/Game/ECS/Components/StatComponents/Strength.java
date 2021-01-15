package Main.Game.ECS.Components.StatComponents;

public class Strength extends StatComponent {
   public Strength(float strength)
   {
       setActiveValue(strength);
       setMaxValue(strength);
   }
}
