package misa.tiled2misa;

/**
 * Represents a single layer in a Tiled map.
 * Stores essential information about the layer including:
 * - `name`: The name of the layer.
 * - `width`: The width (in tiles) of the layer.
 * - `height`: The height (in tiles) of the layer.
 * - `tileData`: A 2D array representing the tile IDs placed on this layer.
 *      Tile IDs are stored as long to accommodate high-bit flags (e.g., flipping, rotation).
 */
public record TiledLayer(String name, int width,
                         int height, long[][] tileData) {}
