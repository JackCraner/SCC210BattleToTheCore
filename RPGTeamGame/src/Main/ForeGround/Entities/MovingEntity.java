package Main.ForeGround.Entities;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

public class MovingEntity extends Entity
{
    Texture entityTexture;
    public MovingEntity(int ID, Vector2f position)
    {
        super(ID);
        setPosition(position);
        setTexture(getEntityTexture());
    }

    public void moveEntity(Vector2f v)
    {
        move(v);
    }


}
