package Main.ForeGround.Entities;

import Main.ForeGround.Interfaces.Illuminator;
import Main.Shader.Light;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

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
