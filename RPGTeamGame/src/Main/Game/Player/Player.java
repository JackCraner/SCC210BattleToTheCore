package Main.Game.Player;

import Main.Game.DataTypes.PositionVector;
import Main.Game.ForeGround.Entities.MEntity;
import Main.Game.ForeGround.Interfaces.Illuminator;
import Main.Game.Shader.Light;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Player extends MEntity implements Illuminator
{
    View playerCamera;
    Light l;
    public Player(int ID, Vector2f position, int viewSize)
    {
        super(ID, position);
        setTexture(getEntityTexture());
        playerCamera = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
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



    public Light getLight() {
        return l;
    }
}
