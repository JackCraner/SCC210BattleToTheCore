package Main.Background.MapGen.MapCreation.MapStylization;

import Main.ForeGround.Entities.Entity;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class CreationCalculator<E>
{



    public float findNearest(Vector2f position, ArrayList<Entity> eList)
    {
        int numberToCheck = 10; // optimization because as the game scans across the map spawning torches, should only need to check against recent spawns (10 most recent)
        float distance = 10000;
        int iStart = 0;
        if(eList.size() < numberToCheck)
        {
            iStart = 0;
        }
        else
        {
            iStart = eList.size() - numberToCheck;
        }
        for (int i = iStart; i < eList.size(); i++)
        {
            float x =(float)Math.pow((eList.get(i).getPosition().x - position.x),2);
            float y =(float)Math.pow((eList.get(i).getPosition().y - position.y),2);
            float  newDistance = (float)Math.pow(x + y, 0.5);

            if (newDistance < distance)
            {
                distance = newDistance;
            }
        }
        return distance;
    }
    public float calculateProbability(float distance, float multiplier)
    {
        return (float)(multiplier* Math.pow(distance,2));
    }


}
