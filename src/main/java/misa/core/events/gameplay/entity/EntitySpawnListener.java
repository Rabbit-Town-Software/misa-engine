package misa.core.events.gameplay.entity;

import misa.entities.GameObject;
import misa.core.events.EventListener;

/**
 * Listens for an entity spawn event and executes logic when an entity is spawned.
 */
@SuppressWarnings("unused")
public class EntitySpawnListener implements EventListener<EntitySpawnEvent>
{
    @Override
    public void handleEvent(EntitySpawnEvent event)
    {
        // Get the entity from the event
        GameObject entity = event.getEntity();

        // Print information about the entity being spawned
        System.out.println("Entity spawned at position: (" + entity.getX() + ", " + entity.getY() + ")");

        // You could add more logic here for handling the entity, like adding it to a list of active entities
        // Example: GameState.addEntity(entity);
    }
}
