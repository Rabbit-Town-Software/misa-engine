package misa.core.events.gameplay.time;

import misa.core.events.Event;

/**
 * Event triggered when the in-game time changes.
 */
public record TimeChangeEvent(int hours, int minutes, int seconds) implements Event
{
    @Override
    public String getEventName()
    {
        return "TimeChangeEvent";
    }
}
