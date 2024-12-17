package misa.core.events.gameplay.tiled;

import misa.core.events.EventListener;

/**
 * Listens for the TileEnterEvent and performs actions when an entity enters a tile.
 */
@SuppressWarnings("unused")
public class TileEnterListener implements EventListener<TileEnterEvent>
{
    @Override
    public void handleEvent(TileEnterEvent event)
    {
        // You can perform actions such as checking if the tile is collidable, triggering animations, etc.
        System.out.println("Entity entered tile at: (" + event.getTileX() + ", " + event.getTileY() + ")");

        // For example, if the tile is collidable, you could trigger a specific behavior
        // Example: Tile tile = TileMap.getTile(event.getTileX(), event.getTileY());
        // if (tile.isCollidable()) { ... }
    }
}
