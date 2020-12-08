package Main.ForeGround.Entities;

import org.jsfml.system.Vector2f;

public class Torch extends Entity
{
    public Torch(int ID, Vector2f pos) {
        super(ID);
        this.setPosition(pos);
        this.setTexture(getEntityTexture());
    }



}
