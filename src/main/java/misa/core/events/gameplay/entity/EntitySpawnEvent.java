package misa.core.events.gameplay.entity;

import misa.entities.GameObject;

/**
 * Event triggered when a GameObject is spawned in the world.
 */
@SuppressWarnings("unused")
public class EntitySpawnEvent extends EntityEvent
{
    /**
     * Constructs an EntitySpawnEvent.
     *
     * @param gameObject The GameObject being spawned.
     */
    public EntitySpawnEvent(GameObject gameObject)
    {
        super(gameObject);
    }
}
