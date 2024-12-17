package misa.core.events.rendering;

import misa.core.events.Event;

/**
 * Represents the event triggered when the rendering process starts.
 */
@SuppressWarnings("unused")
public class RenderStartEvent implements Event
{
    @Override
    public String getEventName()
    {
        return "RenderStartEvent";
    }
}
