package misa.tiled2misa;

/**
 * represents a tileset in a Tiled map
 * <p>
 * a tileset is a collection of images (tiles) that can be used to draw the map
 * each tileset is typically associated with a source file that contains the image data
 * image data includes the image type (e.g., png file) and a `firstGID` (Global Tile ID)
 * - `source`: the path to the source file for the tileset image (e.g., "tiles/terrain.tsx")
 * - `firstGID`: the global ID of the first tile in this tileset
 * the firstGID helps identify which tiles belong to this particular tileset when rendering the map
 * <p>
 * the `TiledTileset` is implemented as a Java record which means:
 * - it is immutable and provides a compact way of creating simple data carriers
 * - the fields `source` and `firstGID` are automatically accessible as methods
 * - the record provides default implementations for `equals()`, `hashCode()`, and `toString()`.
 */
public record TiledTileset(String source, int firstGID) {}
