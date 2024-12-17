package misa.core.events.lifecycle;

import misa.core.events.Event;

/**
 * Represents the event triggered when the game ends.
 */
@SuppressWarnings("unused")
public record GameOverEvent(String reason) implements Event
{
    @Override
    public String getEventName()
    {
        return "GameOverEvent";
    }
}
