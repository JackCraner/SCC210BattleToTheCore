package Main.ForeGround.Entities;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

public class MovingEntity extends Entity
{
    Texture entityTexture;
    Vector2f velocity = new Vector2f(0,0);
    Vector2f gravity = new Vector2f(0.1f,0.1f);
    //stats
    float maxSpeed = 50;

    public MovingEntity(int ID, Vector2f position)
    {
        super(ID);
        setPosition(position);
        setTexture(getEntityTexture());
    }

    public void moveEntity()
    {
        move(velocity);
        friction();
    }

    public void addVelocity(Vector2f v)
    {
        if (checkVelocityLess(velocity.x))
        {
            velocity = new Vector2f(velocity.x + v.x , velocity.y);
        }
        if (checkVelocityLess(velocity.y))
        {
            velocity = new Vector2f(velocity.x , velocity.y+v.y);
        }
        System.out.println(velocity);


    }
    public Vector2f getVelocity()
    {
        return velocity;
    }
    public void friction()
    {
        float vX = 0;
        float vY = 0;


        if (velocity.x > 0)
        {
            vX = (velocity.x - gravity.x) > 0 ? (velocity.x - gravity.x) :  0;
        }
        else if (velocity.x<0)
        {
            vX = (velocity.x + gravity.x) < 0 ? (velocity.x + gravity.x) :  0;
        }

        if (velocity.y> 0)
        {
            vY = (velocity.y - gravity.y) > 0 ? (velocity.y - gravity.y) :  0;
        }
        else if (velocity.y <0)
        {
            vY = (velocity.y +gravity.y) < 0 ? (velocity.y + gravity.y) :  0;
        }



        velocity = new Vector2f(vX,vY);
    }
    public boolean checkVelocityGreater()
    {
        return (((velocity.x)*(velocity.x)) + (velocity.y * velocity.y) > 0);
    }
    public boolean checkVelocityLess(float x)
    {
        return (x*x < maxSpeed);
    }

}
