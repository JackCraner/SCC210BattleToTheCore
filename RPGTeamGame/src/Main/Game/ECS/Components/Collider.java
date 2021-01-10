package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

public class Collider extends Component
{
   // public Boolean isColliding = false;
    public final Boolean events;
    public final Boolean dynamic;
    public final Boolean physics;
    public final Boolean avoidPlayer;

    public Collider()
    {
      this.events = false;
      this.dynamic = false;
      this.physics = true;
      this.avoidPlayer = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, Boolean avoidPlayer)
    {
     this.events = events;
     this.dynamic = dynamic;
     this.physics = phyiscs;
     this.avoidPlayer = avoidPlayer;
    }

    @Override
    public Component clone() {
        return new Collider(events,dynamic,physics,avoidPlayer);
    }
}
