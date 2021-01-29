package Main.Game.ECS.Systems;


import Main.DataTypes.Effects;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StatComponents.EffectComponent;
import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.SpecialComponents.Particles;
import Main.Game.ECS.Components.StatComponents.IsStat;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

import java.util.Iterator;

public class EffectModifierGameSystem extends GameSystem
{
    private static EffectModifierGameSystem systemInstance = new EffectModifierGameSystem();

    public static EffectModifierGameSystem getSystemInstance() {
        return systemInstance;
    }

    private EffectModifierGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(EffectComponent.class));
    }





    @Override
    public void update(float dt)
    {

        for (GameObject g:getGameObjectList())
        {
            EffectComponent objectEffects = g.getComponent(EffectComponent.class);
            if (g.getComponent(CollisionEvent.class) != null)
            {
                CollisionEvent c = g.getComponent(CollisionEvent.class);
                if (BitMasks.checkIfContains(c.getG().getBitmask(), GivenEffect.class))
                {
                    Effects e =c.getG().getComponent(GivenEffect.class).getEffectToGiven();
                    if(BitMasks.checkIfContains(g.getBitmask(), e.getType()))
                    {
                        objectEffects.addEffect(c.getG().getComponent(GivenEffect.class).getEffectToGiven());
                    }

                }
            }


            if (objectEffects.getNewEffects().size() > 0)
            {
                if (!BitMasks.checkIfContains(g.getBitmask(),Particles.class))
                {
                    g.addComponent(new Particles(0.05f));
                }
                Iterator<Effects> newEffectItr = objectEffects.getNewEffects().iterator();
                while (newEffectItr.hasNext())
                {
                    Effects e = newEffectItr.next();
                    IsStat c = (IsStat)g.getComponent(e.getType());
                    c.setStat(c.getStat()+ ((c.getBase() * e.getPercentageModifier())));
                    objectEffects.getEffectsArrayList().add(e);
                    newEffectItr.remove();
                }

            }


            if (objectEffects.getEffectsArrayList().size()>0)
            {
                Iterator<Effects>  currentEffectItr = objectEffects.getEffectsArrayList().iterator();
                while ( currentEffectItr.hasNext())
                {
                    Effects e =  currentEffectItr.next();
                    e.effectClock(dt);
                    if (!e.effectActive())
                    {
                        IsStat c = (IsStat)g.getComponent(e.getType());
                        c.setStat(c.getStat()- ((c.getBase() * e.getPercentageModifier())));
                        currentEffectItr.remove();

                    }

                }



            }
            else
            {
                if (BitMasks.checkIfContains(g.getBitmask(),Particles.class))
                {
                    g.removeComponent(Particles.class);
                }
            }

        }


    }
}
