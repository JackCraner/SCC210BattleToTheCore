package Main.Game.ECS.Components;

import Main.DataTypes.Effects;
import Main.Game.ECS.Entity.Component;

import java.util.ArrayList;

public class EffectComponent extends Component
{
    //stores array of effects to add
    public EffectComponent()
    {

    }
    @Override
    public Component clone()
    {
        return new EffectComponent();
    }
    //flag


}
