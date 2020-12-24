package Main.ForeGround.Entities;

import Main.ForeGround.Interfaces.Illuminator;
import Main.Shader.Light;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Torch extends Entity implements Illuminator
{
    Light l;
    public Torch(int ID, Vector2i pos) {
        super(ID);
        this.setPosition(new Vector2f(pos));
        this.setTexture(getEntityTexture());
        l = new Light(inBlock());
    }

    @Override
    public Light getLight() {
        return l;
    }
}
