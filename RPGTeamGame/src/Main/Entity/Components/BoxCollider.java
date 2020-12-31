package Main.Entity.Components;

import Main.Entity.Component;
import org.jsfml.graphics.FloatRect;

public class BoxCollider extends Component
{
    FloatRect box;
    @Override
    public void start()
    {
        box = getGameObject().getComponent(SpriteController.class).getSpriteComponent().getGlobalBounds();
    }

    @Override
    public void update(float dt) {
        box = getGameObject().getComponent(SpriteController.class).getSpriteComponent().getGlobalBounds();
    }

    public Boolean checkCollisions(FloatRect otherBox)
    {
        FloatRect overlap = box.intersection(otherBox);
        if (overlap != null)
        {
            System.out.println("Hit");
            return true;
        }
        return false;
    }

    public FloatRect getBox() {
        return box;
    }
}
