package Main.Game.ForeGround.Entities;

import org.jsfml.system.Vector2f;

public class Chest extends Entity
{
    public Chest(int uniqueID, Vector2f pos)
    {
        super(uniqueID,3);
        this.setPosition(pos);
    }
    public Chest(Vector2f pos)
    {
        super(0,3);
        this.setPosition(pos);
    }
}
