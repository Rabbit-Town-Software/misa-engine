package misa.core.events.gameplay.tiled;

import misa.core.events.Event;

/**
 * A base class for all tile-related events.
 */
@SuppressWarnings("unused")
public abstract class TileEvent implements Event
{

    protected final int tileX, tileY;

    public TileEvent(int tileX, int tileY)
    {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    @Override
    public String getEventName()
    {
        return this.getClass().getSimpleName();
    }

    // You can add other methods if you need additional data for the tile (e.g., tile type, tile ID, etc.)
}
