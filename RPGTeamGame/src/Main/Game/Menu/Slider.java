package Main.Game.Menu;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Slider extends RectangleShape {
    int id;
    public Slider(int id, Vector2f position, float width, float height)
    {

        super(new Vector2f(width,height));
        this.id = id;
        this.setPosition(position);

    }
    public int getId() {
        return id;
    }

    public boolean isHovered(Vector2i mousePos)
    {
        if (this.getGlobalBounds().contains(new Vector2f(mousePos)))
        {
            return true;
        }
        return false;
    }
}
