package misa.tiled2misa;

/**
 * Represents a tileset in a Tiled map.
 * Stores the source path to the tileset image and the first GID (Global Tile ID).
 */
public record TiledTileset(String source, int firstGID) {}
