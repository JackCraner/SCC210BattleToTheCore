package Main.Game.Menu;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Textimage extends RectangleShape{
    int id;
    public Textimage(int id, Vector2f position, float width, float height) {

        super(new Vector2f(width, height));
        this.id = id;
        this.setPosition(position);
    }
    public int getId() {
        return id;
    }
}