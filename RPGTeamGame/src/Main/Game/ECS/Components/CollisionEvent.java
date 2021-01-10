package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

public class CollisionEvent extends Component
{
    private GameObject g;
    public CollisionEvent(GameObject g)
    {
        this.g = g;
    }

    public GameObject getG() {
        return g;
    }
}
