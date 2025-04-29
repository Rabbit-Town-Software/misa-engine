package misa.data.tiled2misa;

/**
 * Represents a single tile layer inside a Tiled map (.tmx file).
 *
 * <p>
 * Each TiledLayer contains:
 * <ul>
 *     <li><b>name</b> - The human-readable name of the layer (e.g., "Background", "Collisions").</li>
 *     <li><b>width</b> - Width of the layer in tiles.</li>
 *     <li><b>height</b> - Height of the layer in tiles.</li>
 *     <li><b>tileData</b> - 2D array storing tile IDs for each tile position.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Tile IDs are stored as {@code long} values to allow additional metadata,
 * such as tile flipping and rotation flags.
 * </p>
 */
public record TiledLayer(String name, int width, int height, long[][] tileData)
{
    /*
     * 1. `name` - Name of the layer as defined in Tiled.
     * 2. `width` and `height` - Size of the layer in tiles.
     * 3. `tileData` - 2D array where each entry:
     *      - Represents the global tile ID (gid) at that tile location.
     *      - May include metadata flags (e.g., flipped, rotated tiles).
     */
}
