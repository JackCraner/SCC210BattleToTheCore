package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.*;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class PhysicsGameSystem extends GameSystem
{

    private static PhysicsGameSystem systemInstance = new PhysicsGameSystem();
    private ArrayList<FloatRect> walls = new ArrayList<>();


    public static PhysicsGameSystem getSystemInstance() {
        return systemInstance;
    }
    private PhysicsGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class, TransformComponent.class, Collider.class));
    }
    @Override
    public void update(ArrayList<GameEvent> gameEvents)
    {

        ArrayList<FloatRect> rigidBodies = new ArrayList<>();
        ArrayList<Integer> movingRigidBodies = new ArrayList<>();
        int index = 0;
        //Game.getGame().hitboxs.clear(new Color(255,255,255,0));
        for(GameObject g: getGameObjectList())
        {
            TransformComponent t = g.getComponent(TransformComponent.class);
            Vector2f pos = g.getComponent(Position.class).getPosition();
            Collider col = g.getComponent(Collider.class);
            FloatRect body = new FloatRect(pos.x, pos.y, t.getSize().x, t.getSize().y);

            //TEST HITBOX
            /*
            RectangleShape hitbox = new RectangleShape();
            hitbox.setPosition(pos);
            hitbox.setSize(t.getSize());
            hitbox.setOutlineThickness(2);
            hitbox.setOutlineColor(Color.RED);
            hitbox.setFillColor(new Color(255,255,255,0));
            Game.getGame().hitboxs.draw(hitbox);

             */

            rigidBodies.add(body);
            if (col.dynamic == true)
            {
                movingRigidBodies.add(index);
            }

            index ++;

            if (col.getAvoidTimer() >0)
            {
                col.reduceAvoidTime();
                if (col.getAvoidTimer() ==0)
                {
                    col.avoidGameObject = null;
                }
            }

        }

        for(Integer i: movingRigidBodies)
        {
            for (int a = 0; a < rigidBodies.size();a++)
            {
                if (!(a == i))
                {
                    FloatRect collision = rigidBodies.get(i).intersection(rigidBodies.get(a));
                    if (collision != null)
                    {
                        Collider mainCollider = getGameObjectList().get(i).getComponent(Collider.class);
                        Collider collidingWith = getGameObjectList().get(a).getComponent(Collider.class);

                        if (!((collidingWith.avoidGameObject != null && getGameObjectList().get(i).getName() == collidingWith.avoidGameObject.getName())||(mainCollider.avoidGameObject != null && getGameObjectList().get(a).getName() == mainCollider.avoidGameObject.getName())))
                        {
                            if (mainCollider.events == true && collidingWith.events == true)
                            {
                               if ((getGameObjectList().get(i).getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) == 0)
                               {
                                    getGameObjectList().get(i).addComponent(new CollisionEvent(getGameObjectList().get(a))); //only 1 collisionEvent per frame
                               }

                            }

                            if(mainCollider.physics == true && collidingWith.physics == true)
                            {
                                if (mainCollider.dieOnPhysics == true)
                                {
                                    EntityManager.getEntityManagerInstance().removeGameObject(getGameObjectList().get(i));
                                }
                                Vector2f delta = Vector2f.sub(getCenter(rigidBodies.get(a)), getCenter(rigidBodies.get(i)));
                                float intersectX = Math.abs(delta.x) - ((rigidBodies.get(a).width/2) + (rigidBodies.get(i).width/2));
                                float intersectY = Math.abs(delta.y) - ((rigidBodies.get(a).height/2) + (rigidBodies.get(i).height/2));

                                Vector2f collisionVector;
                                if (intersectX > intersectY)
                                {
                                    if (delta.x >0 )
                                    {
                                        collisionVector = new Vector2f(0.5f*intersectX, 0 );
                                    }
                                    else
                                    {
                                        collisionVector = new Vector2f(-0.5f*intersectX, 0 );
                                    }
                                }
                                else
                                {
                                    if (delta.y>0)
                                    {
                                        collisionVector = new Vector2f(0,0.5f*intersectY );
                                    }
                                    else
                                    {
                                        collisionVector = new Vector2f(0,-0.5f*intersectY );
                                    }
                                }

                                Vector2f pos = getGameObjectList().get(i).getComponent(Position.class).getPosition();
                                pos = new Vector2f(pos.x + collisionVector.x, pos.y + collisionVector.y);
                                getGameObjectList().get(i).getComponent(Position.class).updatePosition(pos);

                            }
                        }



                    }
                }
            }


        }

    }
    public Vector2f getCenter(FloatRect r)
    {
        return new Vector2f(r.left + (r.width/2), r.top + (r.height/2));
    }

    public void generateWalls(ArrayList<GameObject> blockList)
    {

    }

}
