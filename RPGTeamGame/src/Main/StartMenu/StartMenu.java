package Main.StartMenu;

import Main.System.Button;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class StartMenu implements Drawable
{

    Button b;
    public StartMenu()
    {
        generateStartMenu();
    }

    public void generateStartMenu()
    {

    }
    public void checkButtons(Vector2i mousePos)
    {
        if (b.isHovered(mousePos))
        {
            b.setFillColor(Color.BLUE);
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(b,renderStates);
    }


}
