package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Components.Shoot;
import Main.Game.ECS.Factory.BitMasks;

import java.util.ArrayList;

public class ShootGameSystem extends GameSystem {

    private static ShootGameSystem shootGameSystem = new ShootGameSystem();

    public static ShootGameSystem getShootGameSystem() {
        return shootGameSystem;
    }

    private ShootGameSystem() {
        setBitMaskRequirement(BitMasks.produceBitMask(Shoot.class));
    }

    @Override
    public void update(ArrayList<GameEvent> gameEvents) {

    }
}



