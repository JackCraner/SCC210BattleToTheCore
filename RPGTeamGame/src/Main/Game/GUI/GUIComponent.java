package Main.Game.GUI;

import Main.Game.ECS.Entity.Component;
import org.jsfml.graphics.Drawable;

public abstract class GUIComponent<T extends Component> implements Drawable
{
    private final T t;
    abstract public void update();

    public GUIComponent(T t)
    {
        this.t = t;
    }

    public T getT()
    {
        return t;
    }
}
