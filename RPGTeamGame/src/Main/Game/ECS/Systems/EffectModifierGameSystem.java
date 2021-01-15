package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.EffectComponent;
import Main.Game.ECS.Components.Particles;
import Main.Game.ECS.Components.StatComponents.StatComponent;
import Main.Game.ECS.Components.Stats;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

public class EffectModifierGameSystem extends GameSystem
{
    private static EffectModifierGameSystem systemInstance = new EffectModifierGameSystem();

    public static EffectModifierGameSystem getSystemInstance() {
        return systemInstance;
    }

    private EffectModifierGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Stats.class, EffectComponent.class));
    }





    @Override
    public void update(float dt)
    {
        for (GameObject g:getGameObjectList())
        {
            Stats objectStats = g.getComponent(Stats.class);
            boolean objectHasEffects = false;
            for(StatComponent statComp: objectStats.getStatComponentArrayList())
            {
                if (statComp.updateEffects(dt))
                {
                    objectHasEffects = true;
                }
            }
            if (!objectHasEffects)
            {
                if (BitMasks.checkIfContains(g.getBitmask(),Particles.class))
                {
                    g.removeComponent(Particles.class);
                }

            }
        }
    }
}
