package Main.Entity.Components;

import Main.Entity.Component;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class SpriteController extends Component implements Drawable
{
    RectangleShape spriteComponent = new RectangleShape();

    public SpriteController(Vector2f size, Color c)
    {
      spriteComponent.setFillColor(c);
      spriteComponent.setSize(size);
    }
    public SpriteController(Texture spriteTexture)
    {
        spriteComponent.setTexture(spriteTexture);
        spriteComponent.setSize(new Vector2f(spriteTexture.getSize()));
    }

    @Override
    public void update(float dt) {
        spriteComponent.setPosition(getGameObject().getPosition());
    }

    @Override
    public void start() {
        spriteComponent.setPosition(getGameObject().getPosition());
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(spriteComponent,renderStates);
    }

    public RectangleShape getSpriteComponent() {
        return spriteComponent;
    }
}
