package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.Effect;
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
        setBitMaskRequirement(BitMasks.produceBitMask(Stats.class,Effect.class));
    }





    @Override
    public void update(float dt)
    {
        for (GameObject g:getGameObjectList())
        {
            Effect effectOnObject = g.getComponent(Effect.class);
            Stats statsOnObject = g.getComponent(Stats.class);
            if (BitMasks.checkIfContainsStats(statsOnObject.getBitMask(), effectOnObject.getEffectType()))
            {
                StatComponent someStat = statsOnObject.getComponent(effectOnObject.getEffectType());
                someStat.applyEffect(effectOnObject.getEffectStrength());
                g.removeComponent(Effect.class);
            }
        }
    }
}
