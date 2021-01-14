package Main.Game.ECS.Components;

import Main.Game.ECS.Components.StatComponents.StatComponent;
import Main.Game.ECS.Entity.Component;
import Main.Game.ECS.Factory.BitMasks;

import java.util.ArrayList;

public class Stats extends Component
{
    int bitMask;
    ArrayList<StatComponent> statComponentArrayList = new ArrayList<>();
    public Stats(StatComponent... stats)
    {
        for (StatComponent stat: stats)
        {
            addComponent(stat);
        }
    }

    public <T extends StatComponent> T getComponent(Class<T> componentClass)
    {
        for (StatComponent c: statComponentArrayList)
        {
            if ((componentClass.isAssignableFrom(c.getClass())))
            {
                return componentClass.cast(c);
            }
        }
        return null;
    }
    public void addComponent(StatComponent c)
    {
        if ((bitMask & BitMasks.getBitMaskStats(c.getClass())) == 0)
        {
            bitMask = bitMask | BitMasks.getBitMaskStats(c.getClass());
            this.statComponentArrayList.add(c);
        }
        else
        {
            System.out.println("ERROR with Stats Component :: Two Statcomponents of same type");
        }

    }
    public void addComponents(StatComponent... c)
    {
        for (StatComponent statComponent: c)
        {
            addComponent(statComponent);
        }
    }
    public int getBitMask()
    {
        return bitMask;
    }

    /*
    float armor;
    float health;
    float damagemultipler;
    float healthRegeneration;           //could be placed with HealthBar component but chests and items wont have health regen so
    float manaRegeneration;


     */
    @Override
    public Component clone() {
        return null;
    }
}
