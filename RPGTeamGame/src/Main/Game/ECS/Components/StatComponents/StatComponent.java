package Main.Game.ECS.Components.StatComponents;

import Main.DataTypes.Effects;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class StatComponent
{
    private ArrayList<Effects> effectsArrayList = new ArrayList<>();
    private float activeValue;
    private float maxValue;
    public void addEffect(Effects e)
    {
        effectsArrayList.add(e);
    }




    //Stats are applied "from base" so stats stack linearly
    // 5 2x Effects = 5x increase
    // instead of
    // 5 2x Effects = 2^5 = 32x increase
    private float applyEffects(float value)
    {
        float valueCopy = value;
       for(Effects e: effectsArrayList)
       {
            valueCopy += ((valueCopy * e.getPercentageModifier() - valueCopy));
       }
       return valueCopy;
    }

    
    protected void setActiveValue(float a)
    {
        activeValue =a;
    }
    protected void setMaxValue(float m)
    {
        maxValue = m;
    }
    protected float getActiveValue()
    {
        return activeValue;
    }
    protected float getMaxValue()
    {
        return maxValue;
    }
    public float getStats()
    {
        return applyEffects(activeValue);
    }

    public boolean updateEffects(float dt)
    {
        if (effectsArrayList.size() == 0)
        {
            return false;
        }
        else
        {
            Iterator<Effects> itr = effectsArrayList.iterator();
            while(itr.hasNext())
            {
                Effects e = itr.next();
                e.effectClock(dt);
                if(!e.effectActive())
                {
                    itr.remove();
                }

            }
            return true;
        }
    }



}
