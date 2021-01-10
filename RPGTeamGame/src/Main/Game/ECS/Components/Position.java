package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;

public class Position extends Component
{

    private Vector2f position;
    private GameObject me;
    public Position(Vector2f position, GameObject me)
    {
        this.position = position;
        this.me = me;
    }
    public Position(Vector2f position)
    {
        this.position = position;
    }
    public Vector2f getPosition()
    {
        return position;
    }
    public void updatePosition(Vector2f position)
    {
        EntityManager.getEntityManagerInstance().updateLeaf(me,position);
        this.position = position;
    }

    public void setMe(GameObject me) {
        this.me = me;
    }

    @Override
    public Component clone() {
        return new Position(position);
    }
}
