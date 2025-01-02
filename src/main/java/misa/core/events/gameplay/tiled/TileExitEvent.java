package misa.core.events.gameplay.tiled;

import misa.core.events.Event;

/**
 * Event triggered when an entity exits a tile.
 */
@SuppressWarnings("unused")
public class TileExitEvent extends TileEvent implements Event
{
    public TileExitEvent(int tileX, int tileY)
    {
        super(tileX, tileY);
    }


    // Similar to TileEnterEvent, you can add additional data if necessary
}
