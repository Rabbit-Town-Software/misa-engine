package misa.core.events.lifecycle;

import misa.core.events.Event;

/**
 * Represents the event triggered when the game is paused.
 */
@SuppressWarnings("unused")
public class GamePauseEvent implements Event
{
    @Override
    public String getEventName()
    {
        return "GamePauseEvent";
    }
}
