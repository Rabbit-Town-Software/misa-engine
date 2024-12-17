package misa.core.events.gameplay.tiled;

import misa.core.events.Event;

/**
 * Event triggered when an entity enters a tile.
 */
@SuppressWarnings("unused")
public class TileEnterEvent extends TileEvent implements Event
{
    public TileEnterEvent(int tileX, int tileY)
    {
        super(tileX, tileY);
    }

    // You can add additional methods or data related to the tile entry event if necessary
}
