package misa.core.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class EventManager
{
    private final Map<String, List<EventListener<? extends Event>>> eventListeners;

    public EventManager(Map<String, List<Runnable>> eventListeners)
    {
        this.eventListeners = new HashMap<>();
    }

    public <T extends Event> void addListener(String eventName, EventListener<T> listener)
    {
        eventListeners.computeIfAbsent(eventName, k -> new ArrayList<>()).add(listener);
    }

    public <T extends Event> void removeListener(String eventName, EventListener<T> listener)
    {
        List<EventListener<? extends Event>> listeners = eventListeners.get(eventName);
        if (listeners != null) listeners.remove(listener);
    }

    public <T extends Event> void triggerEvent(T event)
    {
        List<EventListener<? extends Event>> listeners = eventListeners.get(event.getEventName());
        if (listeners != null)
        {
            for (EventListener<? extends Event> listener : listeners)
            {
                // Safe casting because we store listeners by event type
                @SuppressWarnings("unchecked")
                EventListener<T> typedListener = (EventListener<T>) listener;
                typedListener.handleEvent(event);
            }
        }
    }
}
