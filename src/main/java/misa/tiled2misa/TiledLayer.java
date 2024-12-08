package misa.tiled2misa;

/**
 * Represents a single layer in a Tiled map.
 * <p>
 * - `name`: The name of the layer (e.g., "Background", "Collisions").
 * - `width`: The width of the layer in tiles.
 * - `height`: The height of the layer in tiles.
 * - `tileData`: A 2D array representing tile IDs for each position in the layer.
 * <p>
 * Tile IDs are stored as `long` to accommodate additional metadata such as flipping or rotation flags.
 */
public record TiledLayer(String name, int width, int height, long[][] tileData)
{
    /*
      1. `name` represents the human-readable name of the layer.
      2. `width` and `height` define the size of the layer in tiles.
      3. `tileData` is a 2D array where:
         - Each entry corresponds to a tile at a specific position (x, y).
         - Tile IDs may include flags for flipping or rotation.
     */
}
