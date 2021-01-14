package Main.Game.ECS.Components;

import Main.DataTypes.Avoids;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;
import java.util.Iterator;


public class Collider extends Component
{
   // public Boolean isColliding = false;
    public final Boolean events;
    public final Boolean dynamic;
    public final Boolean physics;
    public ArrayList<Avoids> avoidGameObject = new ArrayList<>();
    public float avoidTimer = 0;
    public final Boolean dieOnPhysics;

    public Collider()
    {
      this.events = false;
      this.dynamic = false;
      this.physics = true;
      this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
    }
    private Collider(Boolean events, Boolean dynamic, Boolean phyiscs, ArrayList<Avoids> a, Boolean dieOnPhysics)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.avoidGameObject = a;
        this.dieOnPhysics = dieOnPhysics;
    }
    public void setAvoidTime(GameObject g, float x)
    {
        avoidGameObject.add(new Avoids(g.getUID(),x));
    }
    public void setAvoidTime(GameObject g)
    {
        avoidGameObject.add(new Avoids(g.getUID()));
    }
    public void reduceAvoidTime(float dt)
    {
        Iterator<Avoids> itr = avoidGameObject.iterator();
        while(itr.hasNext())
        {
            Avoids a = itr.next();
            a.avoidTimeClock(dt);
            if(a.avoidFinished())
            {
                itr.remove();
            }
        }

    }

    public boolean checkGameObject(GameObject g)
    {
        for (Avoids a: avoidGameObject)
        {
            if(a.getGameObjectUIDTOAVOID() == g.getUID())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Component clone() {
        return new Collider(events,dynamic,physics,(ArrayList<Avoids>)avoidGameObject.clone(),dieOnPhysics);
    }
}
