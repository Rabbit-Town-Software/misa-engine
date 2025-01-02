package misa.core.events.lifecycle;

import misa.core.events.Event;

/**
 * Represents the event triggered when the game starts.
 */
@SuppressWarnings("unused")
public class GameStartEvent implements Event
{
    @Override
    public String getEventName()
    {
        return "GameStartEvent";
    }
}
