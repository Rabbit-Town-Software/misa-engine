package misa.core.events.gameplay.entity;

import misa.entities.GameObject;

/**
 * Event triggered when a GameObject is destroyed.
 */
@SuppressWarnings("unused")
public class EntityDestroyEvent extends EntityEvent
{
    /**
     * Constructs an EntityDestroyEvent.
     *
     * @param gameObject The GameObject being destroyed.
     */
    public EntityDestroyEvent(GameObject gameObject)
    {
        super(gameObject);
    }
}
