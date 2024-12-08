package misa.data.tiled2misa;

/**
 * Represents a tileset in a Tiled map.
 * <p>
 * - `source`: File path to the tileset image (e.g., "tileset.png").
 * - `firstGID`: The first Global Tile ID associated with this tileset.
 */
public record TiledTileset(String source, int firstGID)
{
    /**
     * 1. `source` specifies the relative or absolute path to the tileset image.
     * 2. `firstGID` identifies the starting Global Tile ID (GID) for tiles in this tileset.
     */
}
