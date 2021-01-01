package Main.Game.ECS.Entity;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

import java.util.ArrayList;

public class GameObject implements Drawable
{
    private String name;
    private ArrayList<Component> componentList = new ArrayList<>();
    private ArrayList<Class<? extends Component>> componentTypeList = new ArrayList<>();

    public GameObject(String name)
    {
        this.name = name;

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

    public ArrayList<Class<? extends Component>> getComponentTypeList() {
        return componentTypeList;
    }

    public ArrayList<Component> getComponentList() {
        return componentList;
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
        this.componentTypeList.add(c.getClass());
        c.setGameObject(this);
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

    @Override
    public String toString() {
        return name;
    }
}
