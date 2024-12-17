package misa.core.events.input;

import misa.core.events.Event;

@SuppressWarnings("unused")
public abstract class InputEvent implements Event
{
    private final long timestamp; // To record the time of the event

    public InputEvent()
    {
        this.timestamp = System.currentTimeMillis(); // Capture the event timestamp
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    @Override
    public String getEventName()
    {
        return this.getClass().getSimpleName();
    }
}
