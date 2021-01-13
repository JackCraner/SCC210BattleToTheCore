package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.GameObject;

public class Collider extends Component
{
   // public Boolean isColliding = false;
    public final Boolean events;
    public final Boolean dynamic;
    public final Boolean physics;
    public GameObject avoidGameObject;
    public float avoidTimer = 0;
    public final Boolean dieOnPhysics;

    public Collider()
    {
      this.events = false;
      this.dynamic = false;
      this.physics = true;
      this.avoidGameObject = null;
      this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.avoidGameObject = null;
        this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, GameObject avoidPlayer)
    {
     this.events = events;
     this.dynamic = dynamic;
     this.physics = phyiscs;
     this.avoidGameObject = avoidPlayer;
     this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.avoidGameObject = null;
        this.dieOnPhysics = dieOnPhysics;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, GameObject avoidPlayer, Boolean dieOnPhysics)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.avoidGameObject = avoidPlayer;
        this.dieOnPhysics = dieOnPhysics;
    }
    public void setAvoidTime(GameObject g, float x)
    {
        avoidGameObject = g;
        avoidTimer =x;
    }
    public void reduceAvoidTime(float dt)
    {
        avoidTimer -=dt;
    }

    public float getAvoidTimer() {
        return avoidTimer;
    }

    @Override
    public Component clone() {
        return new Collider(events,dynamic,physics, avoidGameObject,dieOnPhysics);
    }
}
