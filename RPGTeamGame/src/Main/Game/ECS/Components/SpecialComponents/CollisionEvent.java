package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
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

    @Override
    public Component clone() {
        return null;
    }
}
