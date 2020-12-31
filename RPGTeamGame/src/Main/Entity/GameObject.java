package Main.Entity;

import Main.Main;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GameObject implements Drawable
{
    private String name;
    private Vector2f position;
    private ArrayList<Component> componentList = new ArrayList<>();

    public GameObject(String name, Vector2f position)
    {
        this.name = name;
        this.position = position;
    }

    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for (Component c: componentList)
        {
            if ((componentClass.isAssignableFrom(c.getClass())))
            {
                return componentClass.cast(c);
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for (int i= 0; i<componentList.size();i++)
        {
            if (componentClass.isAssignableFrom(componentClass.getClass()))
            {
                componentList.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c)
    {
        this.componentList.add(c);
        c.setGameObject(this);
    }
    public void update(float dt)
    {
        for(Component c: componentList)
        {
            c.update(dt);
        }
    }
    public void start()
    {
        for(Component c: componentList)
        {
            c.start();
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for(Component c: componentList)
        {
            if (c instanceof Drawable)
            {
                renderTarget.draw(((Drawable) c), renderStates);

            }
        }
    }
}
