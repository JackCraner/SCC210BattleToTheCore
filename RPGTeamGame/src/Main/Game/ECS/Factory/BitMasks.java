package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.ECS.Components.Pickup;
import Main.Game.ECS.Entity.Component;

import java.util.HashMap;

public class BitMasks
{
    private static BitMasks bitMasksInstance = new BitMasks();

    public static BitMasks getBitMasksInstance() {
        return bitMasksInstance;
    }
    private static HashMap<Class<? extends Component>, Integer> COMPONENTBITMASKS = new HashMap<>();
    static{
        //max 31 components
        COMPONENTBITMASKS.put(Position.class,Integer.parseInt("1", 2));     //1
        COMPONENTBITMASKS.put(TransformComponent.class,Integer.parseInt("10", 2)); //2
        COMPONENTBITMASKS.put(TextureComponent.class,Integer.parseInt("100", 2));  //3
        COMPONENTBITMASKS.put(Collider.class,Integer.parseInt("1000", 2));  //4
        COMPONENTBITMASKS.put(Movement.class,Integer.parseInt("10000", 2));   //5
        COMPONENTBITMASKS.put(Light.class,Integer.parseInt("100000", 2));       //6
        COMPONENTBITMASKS.put(Backpack.class,Integer.parseInt("1000000", 2));       //7
        COMPONENTBITMASKS.put(Pickup.class,Integer.parseInt("100000000", 2));       //8
        COMPONENTBITMASKS.put(CollisionEvent.class,Integer.parseInt("1000000000", 2));          //9
        COMPONENTBITMASKS.put(HealthBar.class,Integer.parseInt("10000000000", 2));      //10
        COMPONENTBITMASKS.put(ManaBar.class,Integer.parseInt("100000000000", 2));       //11
        COMPONENTBITMASKS.put(XPBar.class,Integer.parseInt("1000000000000", 2));        //12
        COMPONENTBITMASKS.put(Damage.class,Integer.parseInt("10000000000000", 2));      //13
        COMPONENTBITMASKS.put(LifeSpan.class,Integer.parseInt("100000000000000", 2));       //14
        COMPONENTBITMASKS.put(Inputs.class,Integer.parseInt("1000000000000000", 2));            //15
        COMPONENTBITMASKS.put(Stats.class,Integer.parseInt("10000000000000000", 2));            //16


    }

    /**
     * Creates a BitMask for a GameObject or GameSystem given one or many Ints
     * The BitMask works by using BitWise OR on all the numbers
     * @param bitMasks  The One or Many Ints to combine into a bitmask
     * @return the combined bitmask
     */
    public static int produceBitMask(int... bitMasks)
    {
        int newMask = 0;
        for (int i: bitMasks)
        {
            newMask |= i;
        }
        return newMask;
    }

    /**
     * Also Creates a BitMask for a GameObject or GameSystem but given one or many Components
     * Uses the unique integer values found in the HashMap for each given component
     *
     * @param bitMasks The Components used to make the BitMask
     * @return The BitMask
     */
    public static int produceBitMask(Class<? extends Component>... bitMasks)
    {
        int newMask = 0;
        for (Class<? extends  Component> c : bitMasks)
        {
            newMask |= COMPONENTBITMASKS.get(c);
        }
        return newMask;
    }

     public static int getBitMask(Class<? extends Component> c)
     {
         return COMPONENTBITMASKS.get(c);
     }

}
