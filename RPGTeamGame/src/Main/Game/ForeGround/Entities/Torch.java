package Main.Game.ForeGround.Entities;

import Main.Game.ForeGround.Interfaces.Illuminator;
import Main.Game.Shader.Light;
import org.jsfml.system.Vector2f;

public class Torch extends Entity implements Illuminator
{
    Light l;
    public Torch(Vector2f pos) {
        super(2);
        this.setPosition(pos);
        //l = new Light(pos);
        l = new Light(this.getCenter());
    }

    @Override
    public Light getLight() {
        return l;
    }
}
