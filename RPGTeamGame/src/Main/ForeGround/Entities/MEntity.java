package Main.ForeGround.Entities;

import Main.Physics.RigidBody.RigidBody;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

public class MEntity extends Entity
{
    Texture entityTexture;
    Vector2f velocity = new Vector2f(0,0);
    Vector2f gravity = new Vector2f(0.1f,0.1f);
    //stats
    float maxSpeed = 20;

    RigidBody body = null;

    public MEntity(int ID, Vector2f position)
    {
        super(ID);
        setPosition(position);

        body = new RigidBody();
        body.setTransform(position);
        body.setMass(1.0f);
    }

    public void move()
    {
        move(velocity);
        body.setTransform(getPosition());
        friction();
    }

    public void setVelocity(Vector2f v)
    {
        if (checkVelocityLess(velocity.x))
        {
            velocity = new Vector2f(velocity.x + v.x , velocity.y);
        }
        if (checkVelocityLess(velocity.y))
        {
            velocity = new Vector2f(velocity.x , velocity.y+v.y);
        }


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
    public boolean hasMoved(Vector2f nothing)
    {
        return !(((velocity.x)*(velocity.x)) + (velocity.y * velocity.y) > 0);
    }
    public boolean checkVelocityLess(float x)
    {
        return (x*x < maxSpeed);
    }

    public void updatePosition()
    {
        setPosition(body.getPosition());
    }

    public RigidBody getBody() {
        return body;
    }
}
