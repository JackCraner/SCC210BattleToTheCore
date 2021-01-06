package Main.Game.ECS.Entity;

import Main.Game.ECS.Components.Movement;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

public class GameObject implements Drawable
{
    private String name;
    private Vector2f position;
    private Vector2f size;

    private int bitmask = 0;


    private ArrayList<Component> componentList = new ArrayList<>();



    private ArrayList<Class<? extends Component>> componentTypeList = new ArrayList<>();

    public GameObject(String name, Vector2f position, Vector2f size)
    {
        this.name = name;
        this.position = position;
        this.size = size;
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
        bitmask = bitmask | EntityManager.getEntityManagerInstance().componentBitMask.get(c.getClass());
        this.componentList.add(c);
        this.componentTypeList.add(c.getClass());
        c.setGameObject(this);
    }

    public String getName() {
        return name;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void setPosition(Vector2f position)
    {
        this.position = position;
    }

    public int getBitmask() {
        return bitmask;
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
