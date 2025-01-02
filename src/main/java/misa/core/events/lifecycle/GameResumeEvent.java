package misa.core.events.lifecycle;

import misa.core.events.Event;

/**
 * Represents the event triggered when the game is resumed after being paused.
 */
@SuppressWarnings("unused")
public class GameResumeEvent implements Event
{
    @Override
    public String getEventName()
    {
        return "GameResumeEvent";
    }
}
