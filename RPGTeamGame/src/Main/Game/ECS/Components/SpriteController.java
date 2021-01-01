package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class SpriteController extends Component
{
    private RectangleShape spriteComponent = new RectangleShape();

    public SpriteController(Vector2f size, Color c, Vector2f position)
    {
      spriteComponent.setFillColor(c);
      spriteComponent.setSize(size);
      spriteComponent.setPosition(position);
    }
    public SpriteController(Texture spriteTexture, Vector2f position)
    {
        spriteComponent.setTexture(spriteTexture);
        spriteComponent.setSize(new Vector2f(spriteTexture.getSize()));
        spriteComponent.setPosition(position);
    }



    public RectangleShape getSpriteComponent()
    {
        return spriteComponent;
    }
}
