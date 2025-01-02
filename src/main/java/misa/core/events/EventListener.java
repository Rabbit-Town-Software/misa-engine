package misa.core.events;

@SuppressWarnings("unused")
public interface EventListener<T extends Event>
{
    void handleEvent(T event);
}
