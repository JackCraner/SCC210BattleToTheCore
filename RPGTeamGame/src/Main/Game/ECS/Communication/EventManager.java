package Main.Game.ECS.Communication;

import Main.Game.ECS.Communication.Events.GameEvent;
import Main.Game.ECS.Communication.Events.GameEventTypes;
import Main.Game.ECS.Systems.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager
{

    private static EventManager eventManagerInstance = new EventManager();

    private static HashMap<Class<? extends GameSystem>, ArrayList<GameEventTypes>> publisherSubscriberMap = new HashMap<>();
    static{
        //What Messages the system should TAKE IN
        publisherSubscriberMap.put(MovementGameSystem.class, gameEventArray());
        publisherSubscriberMap.put(PhysicsGameSystem.class, gameEventArray());
        publisherSubscriberMap.put(BackpackGameSystem.class, gameEventArray());
        publisherSubscriberMap.put(CombatGameSystem.class, gameEventArray());
        publisherSubscriberMap.put(ShootGameSystem.class, gameEventArray());
        publisherSubscriberMap.put(RendererGameSystem.class, gameEventArray(GameEventTypes.ShaderEvent));
        publisherSubscriberMap.put(LightingGameSystem.class, gameEventArray(GameEventTypes.TextureEvent));

    }
    private ArrayList<GameEvent> activeEventList = new ArrayList<>();
    private ArrayList<GameEvent> pollingEventList = new ArrayList<>();

    public static EventManager getEventManagerInstance() {
        return eventManagerInstance;
    }

    private EventManager()
    {

    }

    public void addEvent(GameEvent event)
    {
       pollingEventList.add(event);

    }
    public ArrayList<GameEvent> getEvents(Class<? extends GameSystem> systemClass)
    {
        ArrayList<GameEvent> systemEvents = new ArrayList<>();
        ArrayList<GameEventTypes> systemEventsSubscribed = publisherSubscriberMap.get(systemClass);
        for (GameEvent ge : activeEventList)
        {
            if (systemEventsSubscribed.contains(ge.getEventType()))
            {
                systemEvents.add(ge);
            }
        }
        return systemEvents;
    }
    public static ArrayList<GameEventTypes> gameEventArray(GameEventTypes... c)
    {
        ArrayList<GameEventTypes> gameEventList = new ArrayList<>();
        for (GameEventTypes gameEvent: c)
        {
            gameEventList.add(gameEvent);
        }
        return gameEventList;
    }
    public void emptyEvents()
    {
        activeEventList = new ArrayList<>(pollingEventList);
        pollingEventList.clear();
    }



}
