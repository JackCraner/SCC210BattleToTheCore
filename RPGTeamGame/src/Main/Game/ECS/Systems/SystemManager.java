package Main.Game.ECS.Systems;

import Main.Game.ECS.Communication.EventManager;
import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

/**
 * SYSTEMMANAGER
 *
 * -Handles all the Systems in the game
 *  - Updating and Generating Events
 * -Handles SystemEvents (TO DO)
 *
 * Whenever a new System is created
 * It must be added to this class to work
 *
 */
public class SystemManager
{
    private static SystemManager managerInstance = new SystemManager();
    private static EventManager EVENTMANAGER = EventManager.getEventManagerInstance();
    private ArrayList<GameSystem> systemList = new ArrayList<>();
    public static SystemManager getSystemManagerInstance()
    {
        return managerInstance;
    }

    /**
     * Add All systems here
     */
    private SystemManager()
    {
        systemList.add(MovementGameSystem.getSystemInstance());
        systemList.add(PhysicsGameSystem.getSystemInstance());
        systemList.add(RendererGameSystem.getSystemInstance());
        systemList.add(LightingGameSystem.getLightingGameSystem());
        systemList.add(ShootGameSystem.getShootGameSystem());


    }

    /**
     * Adds a given GameObject to all the Systems in which it meets the requirements
     * @param g The GameObject to be added to the systems
     */
    public void addGameObjectTOSYSTEMS(GameObject g)
    {
        for (GameSystem s: systemList)
        {
            if ((g.getBitmask() & s.getBitMaskRequirement()) == s.getBitMaskRequirement())
            {
                s.addGameObject(g);
            }
        }
    }

    /**
     * Updates all Systems
     * -- Called every frame
     */
    public void updateSystems()
    {
        for(GameSystem s: systemList)
        {
            ArrayList<GameEvent> events = EVENTMANAGER.getEvents(s.getClass());
            s.update(events);

        }
        flushSystems();
    }

    /**
     * Empties all the ArrayLists in all the systems ready for next Frames data
     */
    public void flushSystems()
    {
        for(GameSystem s: systemList)
        {
            s.refreshGameObjects();
        }
        EVENTMANAGER.emptyEvents();
    }

}
