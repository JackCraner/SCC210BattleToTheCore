package Main.Game.ForeGround.Entities;

import org.jsfml.system.Vector2f;

public class SaveShrine extends Entity
{
    public SaveShrine(Vector2f position) {
        super(0,4);
        this.setPosition(position);
        this.setScale(2,2);
    }
}
