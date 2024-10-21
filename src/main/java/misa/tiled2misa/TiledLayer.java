package misa.tiled2misa;

/**
 * represents a single layer in a Tiled map
 * <p>
 * a Tiled map can consist of multiple layers
 * each layer contains a grid of tiles that forms part of the map's visual layout
 * each `TiledLayer` stores essential information about the layer including:
 * - `name`: the name of the layer
 * - `width`: the width (tiles) of the layer
 * - `height`: the height (tiles) of the layer
 * - `tileData`: a 2D array representing the tile IDs placed on this layer
 *      each entry corresponds to a tile at a specific position in the grid,
 *      where the value indicates the Global Tile ID (GID) used to render that tile
 * <p>
 * the `TiledLayer` is implemented as a Java record which means:
 * - it is immutable and provides a compact way of defining simple data carriers
 * - the fields `name`, `width`, `height`, and `tileData` are automatically accessible as methods
 * - the record provides default implementations for `equals()`, `hashCode()`, and `toString()`
 */
public record TiledLayer(String name,
                         int width, int height,
                         int[][] tileData) {}
