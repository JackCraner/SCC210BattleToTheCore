package Main.ForeGround;

import Main.ForeGround.Entities.Player;
import Main.ForeGround.Entities.Torch;
import Main.ForeGround.Interfaces.Illuminator;
import Main.Main;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

public class Foreground implements Drawable
{
    Torch test = new Torch(2,new Vector2i(100,100));
    ArrayList<Illuminator> lightList = new ArrayList<>();


    public Foreground(Player p)
    {
        lightList.add(p);
        lightList.add(test);
    }

    public ArrayList<Illuminator> getLightList() {
        return lightList;
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        test.draw(renderTarget,renderStates);
    }
}
