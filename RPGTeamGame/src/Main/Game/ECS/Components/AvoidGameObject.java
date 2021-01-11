package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

public class AvoidGameObject extends Component
{

    GameObject g;
    int timer =-1;

    public AvoidGameObject(GameObject g, int timer)
    {
        this.timer = timer;
        this.g = g;
    }
    public AvoidGameObject(GameObject g)
    {
        this.g = g;
    }
    public Boolean checkGameObject(GameObject newg)
    {
        return (!(newg.equals(g) && (timer > 0)));
    }
    public void countTimer()
    {
        if(timer>0)
        {
            timer--;
        }
    }
    
    @Override
    public Component clone() {
        if (timer == -1)
        {
            return new AvoidGameObject(g);
        }
        else
        {
            return new AvoidGameObject(g,timer);
        }
    }
}
