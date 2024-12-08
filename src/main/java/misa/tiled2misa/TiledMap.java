package misa.tiled2misa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a parsed Tiled map.
 * <p>
 * - Stores information about map dimensions, tile sizes, layers, tilesets, and objects.
 * - Provides read-only access to its attributes after construction via the builder pattern.
 */
@SuppressWarnings("unused")
public class TiledMap
{
    private static final Logger LOGGER = Logger.getLogger(TiledMap.class.getName());

    private final int width;  // Width of the map in tiles
    private final int height; // Height of the map in tiles
    private final int tileWidth;  // Width of individual tiles in pixels
    private final int tileHeight; // Height of individual tiles in pixels
    private final List<TiledLayer> layers;    // List of layers in the map
    private final List<TiledTileset> tilesets; // List of tilesets used by the map
    private final List<TiledObject> objects;  // List of objects defined in the map

    /**
     * Private constructor used by the Builder class.
     * <p>
     * Validates the attributes and logs warnings if any critical data is missing.
     */
    private TiledMap(Builder builder)
    {
        this.width = builder.width;
        this.height = builder.height;
        this.tileWidth = builder.tileWidth;
        this.tileHeight = builder.tileHeight;
        this.layers = Collections.unmodifiableList(builder.layers);
        this.tilesets = Collections.unmodifiableList(builder.tilesets);
        this.objects = Collections.unmodifiableList(builder.objects);

        // Log map creation details and potential issues
        LOGGER.info("TiledMap created with dimensions: " +
                width + "x" + height + " and tile size: " + tileWidth + "x" + tileHeight);

        if (layers.isEmpty())
        {
            LOGGER.warning("TiledMap has no layers. Rendering may fail.");
        }
        if (tilesets.isEmpty())
        {
            LOGGER.warning("TiledMap has no tilesets. Ensure the map was parsed correctly.");
        }
        if (objects.isEmpty())
        {
            LOGGER.info("TiledMap contains no objects.");
        }
    }

    // Getters for map attributes
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    public List<TiledLayer> getLayers() { return layers; }
    public List<TiledTileset> getTilesets() { return tilesets; }
    public List<TiledObject> getObjects() { return objects; }

    /**
     * Builder class for creating instances of TiledMap.
     * <p>
     * Ensures all attributes are properly set before the TiledMap is constructed.
     */
    public static class Builder
    {
        private int width, height;
        private int tileWidth, tileHeight;
        private List<TiledLayer> layers = new ArrayList<>();
        private List<TiledTileset> tilesets = new ArrayList<>();
        private List<TiledObject> objects = new ArrayList<>();

        // Setters for attributes, returning the Builder instance for chaining
        public Builder setWidth(int width) { this.width = width; return this; }
        public Builder setHeight(int height) { this.height = height; return this; }
        public Builder setTileWidth(int tileWidth) { this.tileWidth = tileWidth; return this; }
        public Builder setTileHeight(int tileHeight) { this.tileHeight = tileHeight; return this; }
        public Builder setLayers(List<TiledLayer> layers) { this.layers = new ArrayList<>(layers); return this; }
        public Builder setTilesets(List<TiledTileset> tilesets) { this.tilesets = new ArrayList<>(tilesets); return this; }
        public Builder setObjects(List<TiledObject> objects) { this.objects = new ArrayList<>(objects); return this; }

        /**
         * Builds and returns a TiledMap instance.
         * <p>
         * 1. Ensure all critical attributes (e.g., dimensions) are valid.
         * 2. Log warnings if any critical data (e.g., layers or tilesets) is missing.
         * 3. Return a new TiledMap instance.
         */
        public TiledMap build()
        {
            if (width <= 0 || height <= 0)
            {
                LOGGER.warning("Invalid map dimensions: " + width + "x" + height);
            }
            if (tileWidth <= 0 || tileHeight <= 0)
            {
                LOGGER.warning("Invalid tile dimensions: " + tileWidth + "x" + tileHeight);
            }
            return new TiledMap(this);
        }
    }
}
