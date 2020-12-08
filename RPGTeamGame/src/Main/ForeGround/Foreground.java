package Main.ForeGround;

import Main.ForeGround.Entities.Torch;
import Main.Main;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

public class Foreground implements Drawable
{
    Torch test = new Torch(2,new Vector2f(100,100));

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        test.draw(renderTarget,renderStates);
    }
}
