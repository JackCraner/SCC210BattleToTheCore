package Main.Game.ECS.Communication;

import Main.Game.ECS.Systems.GameSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager
{

    private EventManager eventManagerInstance = new EventManager();
    private HashMap<GameSystem, ArrayList<Class<? extends GameEvent>>> publisherSubscriberMap = new HashMap<>();
    private ArrayList<GameEvent> gameEventList = new ArrayList<>();


    public EventManager getEventManagerInstance() {
        return eventManagerInstance;
    }

    private EventManager()
    {

    }

    public void addEvent(GameEvent event)
    {
        //loop through hashmap
        // for every system that is subscribed to the event, trigger the systems update(event) by passing the event in

    }


}
