package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.BoxCollider;
import Main.Game.ECS.Components.Movement;
import Main.Game.ECS.Components.Position;
import Main.Game.ECS.Components.Size;
import Main.Game.ECS.Entity.Component;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

public class PhysicsSystem extends GameSystem
{

    private static PhysicsSystem systemInstance = new PhysicsSystem();

    public static PhysicsSystem getSystemInstance() {
        return systemInstance;
    }

    @Override
    public ArrayList<Class<? extends Component>> systemComponentRequirements() {
        ArrayList<Class<? extends Component>> c = new ArrayList<>();
        c.add(Position.class);
        c.add(Size.class);
        c.add(BoxCollider.class);
        return c;
    }

    @Override
    public void update()
    {

        ArrayList<FloatRect> rigidBodies = new ArrayList<>();
        ArrayList<Integer> movingRigidBodies = new ArrayList<>();
        int index = 0;
        for(Component[] cA: getComponentArrayList())
        {
            Vector2f pos = ((Position)cA[0]).position;
            Vector2f size = ((Size)cA[1]).size;

            FloatRect body = new FloatRect(pos.x, pos.y, size.x, size.y);
            rigidBodies.add(body);
            if (cA[0].getGameObject().getComponent(Movement.class) != null)
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
                        Vector2f delta = Vector2f.sub(new Vector2f(rigidBodies.get(a).left,rigidBodies.get(a).top)  , new Vector2f(rigidBodies.get(i).left,rigidBodies.get(i).top ));
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
                        Vector2f pos = ((Position)getComponentArrayList().get(i)[0]).position;
                        pos = new Vector2f(pos.x + collisionVector.x, pos.y + collisionVector.y);
                        ((Position)getComponentArrayList().get(i)[0]).position = pos;


                    }
                }
            }


        }

    }

    @Override
    public void update(Event event) {

    }
}
