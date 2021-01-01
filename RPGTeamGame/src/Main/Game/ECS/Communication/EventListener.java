package Main.Game.ECS.Communication;

import Main.Game.ECS.Systems.GameSystem;

import java.util.Hashtable;
import java.util.List;

public class EventListener
{

    private static EventListener listenerInstance = new EventListener();


    private Hashtable<GameEvent, List<GameSystem>> subscriberList;



    public static EventListener getInstance()
    {
        return listenerInstance;
    }




}
