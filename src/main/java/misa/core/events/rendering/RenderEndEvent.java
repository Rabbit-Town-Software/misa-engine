package misa.core.events.rendering;

import misa.core.events.Event;

/**
 * Represents the event triggered when the rendering process ends.
 */
@SuppressWarnings("unused")
public class RenderEndEvent implements Event
{
    @Override
    public String getEventName()
    {
        return "RenderEndEvent";
    }
}
