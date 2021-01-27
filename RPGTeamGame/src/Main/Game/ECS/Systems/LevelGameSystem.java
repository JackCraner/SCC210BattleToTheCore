package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

public class LevelGameSystem extends GameSystem
{

    private static LevelGameSystem systemInstance = new LevelGameSystem();
    public static LevelGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private LevelGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(Level.class));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            Level objectLevel = g.getComponent(Level.class);
            float xpNeededToLevel = objectLevel.getCurrentLevel() * 5;
            if (objectLevel.getCurrentLevel() > xpNeededToLevel)
            {
                objectLevel.levelUP();
                /*
                if (BitMasks.checkIfContains(g.getBitmask(), Stats.class))
                {
                    Stats objectStats = g.getComponent(Stats.class);
                    for (StatComponent objectStatComponents: objectStats.getStatComponentArrayList())
                    {
                        objectStatComponents.levelUpStat(10);
                    }
                }

                 */
            }
        }

    }
}
