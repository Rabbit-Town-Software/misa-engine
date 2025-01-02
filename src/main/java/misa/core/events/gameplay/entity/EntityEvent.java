package misa.core.events.gameplay.entity;

import misa.core.events.Event;
import misa.entities.GameObject;

@SuppressWarnings("unused")
public abstract class EntityEvent implements Event
{
    private final GameObject entity;

    public EntityEvent(GameObject entity)
    {
        this.entity = entity;
    }

    public GameObject getEntity()
    {
        return entity;
    }

    @Override
    public String getEventName()
    {
        return this.getClass().getSimpleName();
    }
}
