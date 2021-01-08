package Main.Game.ECS.Communication.Events;

import Main.Game.ECS.Communication.Events.GameEventTypes;

public class GameEvent<E>
{
    private final E data;
    private final GameEventTypes eventType;

    public GameEvent(E data, GameEventTypes eventType)
    {
        this.data = data;
        this.eventType = eventType;
    }

    public E getData() {
        return data;
    }

    public GameEventTypes getEventType() {
        return eventType;
    }
}
