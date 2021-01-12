package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.Effects;
import Main.Game.ECS.Factory.BitMasks;

public class EffectModifierGameSystem extends GameSystem
{
    private static EffectModifierGameSystem systemInstance = new EffectModifierGameSystem();

    public static EffectModifierGameSystem getSystemInstance() {
        return systemInstance;
    }

    private EffectModifierGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Effects.class));
    }
    @Override
    public void update(float dt) {

    }
}
