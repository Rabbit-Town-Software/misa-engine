package misa.core.events.gameplay.entity;

import misa.entities.GameObject;
import misa.core.events.EventListener;

/**
 * Listens for an entity destroy event and executes logic when an entity is destroyed.
 */
@SuppressWarnings("unused")
public class EntityDestroyListener implements EventListener<EntityDestroyEvent>
{
    @Override
    public void handleEvent(EntityDestroyEvent event)
    {
        // Get the entity from the event
        GameObject entity = event.getEntity();

        // Print information about the entity being destroyed
        System.out.println("Entity destroyed at position: (" + entity.getX() + ", " + entity.getY() + ")");

        // You could add more logic here for cleaning up the entity
        // Example: GameState.removeEntity(entity);
    }
}
