package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.Collider;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Entity.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.Game;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

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
        setBitMaskRequirement(EntityManager.getEntityManagerInstance().produceBitMask(Collider.class));
    }
    @Override
    public void update()
    {

        ArrayList<FloatRect> rigidBodies = new ArrayList<>();
        ArrayList<Integer> movingRigidBodies = new ArrayList<>();
        int index = 0;
        for(GameObject g: getGameObjectList())
        {
            Vector2f size = g.getSize();
            Vector2f pos = g.getPosition();


            FloatRect body = new FloatRect(pos.x, pos.y, size.x, size.y);


            rigidBodies.add(body);
            if (g.getComponent(Movement.class) != null)
            {
                movingRigidBodies.add(index);
            }

            index ++;



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

                        Vector2f pos = getGameObjectList().get(i).getPosition();
                        pos = new Vector2f(pos.x + collisionVector.x, pos.y + collisionVector.y);
                        getGameObjectList().get(i).setPosition(pos);


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
    @Override
    public void update(Event event) {

    }
}