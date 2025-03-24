package misa.core.events.gameplay.entity;

import misa.entities.GameObject;
import misa.core.events.EventListener;

/**
 * Listens for an entity destroy event and executes logic when an entity is destroyed.
 */
@SuppressWarnings("unused")
public class EntityDestroyListener implements EventListener<EntityDestroyEvent>
{
    public EntityDestroyListener()
    {
        // Constructor - no need for LuaEventHandler or Lua script path.
    }

    @Override
    public void handleEvent(EntityDestroyEvent event)
    {
        // Handle the event in Java (no Lua logic needed)
        GameObject entity = event.getEntity();
        System.out.println("Entity destroyed at position: (" + entity.getCoordinateX() + ", " +
                entity.getCoordinateY() + ")");

        // You can add more logic here for cleaning up or removing the entity from game state.
        // Example: GameState.removeEntity(entity);
    }
}
