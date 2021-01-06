package Main.Game.ECS.Systems;

import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class SystemManager
{
    private static SystemManager managerInstance = new SystemManager();
    private ArrayList<GameSystem> systemList = new ArrayList<>();
    public static SystemManager getSystemManagerInstance()
    {
        return managerInstance;
    }
    private SystemManager()
    {
        systemList.add(MovementGameSystem.getSystemInstance());
        systemList.add(PhysicsGameSystem.getSystemInstance());
        systemList.add(RendererGameSystem.getSystemInstance());

    }
    public void addGameObjectTOSYSTEMS(GameObject g)
    {
        for (GameSystem s: systemList)
        {
            if ((g.getBitmask() & s.getBitMaskRequirement()) != 0)
            {
                s.addGameObject(g);
            }
        }
    }
    public void updateSystems()
    {
        for(GameSystem s: systemList)
        {
            s.update();
        }
        flushSystems();
    }
    public void flushSystems()
    {
        for(GameSystem s: systemList)
        {
            s.refreshGameObjects();
        }
    }


}
