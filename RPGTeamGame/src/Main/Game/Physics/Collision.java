package Main.Game.Physics;

import org.jsfml.graphics.FloatRect;

public class Collision
{





    public Boolean checkCollision(CollisionBox cB1, CollisionBox cB2)
    {
        FloatRect intersection = cB1.getBox().intersection(cB2.getBox());
        if(intersection != null)
        {


        }
        return true;
    }


}
