package misa.core.events.gameplay.tiled;

import misa.core.events.EventListener;

/**
 * Listens for the TileExitEvent and performs actions when an entity exits a tile.
 */
@SuppressWarnings("unused")
public class TileExitListener implements EventListener<TileExitEvent>
{
    @Override
    public void handleEvent(TileExitEvent event)
    {
        // Perform actions when the entity exits a tile
        System.out.println("Entity exited tile at: (" + event.getTileX() + ", " + event.getTileY() + ")");

        // You can check conditions or reset tile properties if necessary
    }
}
