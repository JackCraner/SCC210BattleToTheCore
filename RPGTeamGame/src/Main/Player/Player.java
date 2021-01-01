package Main.Player;

import Main.DataTypes.PositionVector;
import Main.ForeGround.Entities.MEntity;
import Main.ForeGround.Interfaces.Illuminator;
import Main.Game;
import Main.Shader.Light;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Player extends MEntity implements Illuminator
{
    View playerCamera;
    PositionVector pos;
    Light l;
    public Player(int ID, Vector2f position, int viewSize)
    {
        super(ID, position);
        setPosition(position);
        setTexture(getEntityTexture());
        playerCamera = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        playerCamera.setCenter(this.getPosition());
        l = new Light(this);
    }
    public View getpView()
    {
        return playerCamera;
    }
    public void zoom(int x)
    {
        float newSize = playerCamera.getSize().x + x;
        playerCamera.setSize(new Vector2f(newSize,newSize));
    }
    public void moveEntity()
    {
        playerCamera.setCenter(this.getPosition());
        l.setPosition(this.getPosition());
    }

    @Override
    public void updatePosition()
    {
        setPosition(this.getBody().getPosition());
        moveEntity();
    }


    public Light getLight() {
        return l;
    }
}
