package Main.ForeGround.Entities;

import Main.DataTypes.PositionVector;
import Main.Game;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Player extends MovingEntity
{
    View playerCamera;
    PositionVector pos;
    public Player(int ID, Vector2f position, int viewSize)
    {
        super(ID, position);
        setPosition(position);
        setTexture(getEntityTexture());
        playerCamera = new View(new Vector2f(viewSize/2,viewSize/2), new Vector2f(viewSize,viewSize));
        playerCamera.setCenter(this.getPosition());
    }

    public View getpView()
    {
        return playerCamera;
    }

    public void moveEntity()
    {
        playerCamera.setCenter(this.getPosition());
    }

}
