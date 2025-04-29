package misa.data.tiled2misa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a parsed Tiled map (.tmx) containing layers, tilesets, and objects.
 *
 * <p>
 * Once created, a TiledMap is immutable. Access its layers, tilesets, and objects
 * through unmodifiable lists.
 * </p>
 *
 * <p>
 * Use {@link TiledMap.Builder} to construct a new TiledMap safely.
 * </p>
 */
@SuppressWarnings("unused")
public class TiledMap
{
    private static final Logger LOGGER = Logger.getLogger(TiledMap.class.getName());

    // Basic map properties
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;

    // Parsed map data
    private final List<TiledLayer> layers;
    private final List<TiledTileset> tilesets;
    private final List<TiledObject> objects;

    /**
     * Private constructor — use Builder to create TiledMap instances.
     *
     * @param builder The populated Builder instance.
     */
    private TiledMap(Builder builder)
    {
        // Copy values from the builder
        this.width = builder.width;
        this.height = builder.height;
        this.tileWidth = builder.tileWidth;
        this.tileHeight = builder.tileHeight;
        this.layers = Collections.unmodifiableList(builder.layers);
        this.tilesets = Collections.unmodifiableList(builder.tilesets);
        this.objects = Collections.unmodifiableList(builder.objects);

        // Log map construction info
        LOGGER.info("TiledMap constructed:");
        LOGGER.info(" - Map Size: " + width + " x " + height + " tiles");
        LOGGER.info(" - Tile Size: " + tileWidth + " x " + tileHeight + " pixels");
        LOGGER.info(" - Layers: " + layers.size());
        LOGGER.info(" - Tilesets: " + tilesets.size());
        LOGGER.info(" - Objects: " + objects.size());

        // Check for missing parts (warn if needed)
        if (layers.isEmpty())
        {
            LOGGER.warning("TiledMap has no layers. Nothing will render.");
        }

        if (tilesets.isEmpty())
        {
            LOGGER.warning("TiledMap has no tilesets. Check if TSX files were loaded properly.");
        }

        if (objects.isEmpty())
        {
            LOGGER.info("TiledMap has no objects defined. That's okay if intentional.");
        }
    }

    // ----------- Getters -----------

    /**
     * @return Map width in tiles.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @return Map height in tiles.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @return Width of each tile in pixels.
     */
    public int getTileWidth()
    {
        return tileWidth;
    }

    /**
     * @return Height of each tile in pixels.
     */
    public int getTileHeight()
    {
        return tileHeight;
    }

    /**
     * @return Unmodifiable list of all tile layers.
     */
    public List<TiledLayer> getLayers()
    {
        return layers;
    }

    /**
     * @return Unmodifiable list of all tilesets used in the map.
     */
    public List<TiledTileset> getTilesets()
    {
        return tilesets;
    }

    /**
     * @return Unmodifiable list of all objects placed in the map.
     */
    public List<TiledObject> getObjects()
    {
        return objects;
    }

    /**
     * Builder for constructing immutable {@link TiledMap} instances.
     *
     * <p>
     * Example usage:
     * <pre>
     * TiledMap map = new TiledMap.Builder()
     *      .setWidth(100)
     *      .setHeight(50)
     *      .setTileWidth(16)
     *      .setTileHeight(16)
     *      .setLayers(layers)
     *      .setTilesets(tilesets)
     *      .setObjects(objects)
     *      .build();
     * </pre>
     * </p>
     */
    public static class Builder
    {
        private int width, height;
        private int tileWidth, tileHeight;
        private List<TiledLayer> layers = new ArrayList<>();
        private List<TiledTileset> tilesets = new ArrayList<>();
        private List<TiledObject> objects = new ArrayList<>();

        // ----------- Builder Setters -----------

        /**
         * Sets the map width (in tiles).
         *
         * @param width The width.
         * @return This builder instance.
         */
        public Builder setWidth(int width)
        {
            LOGGER.fine("Builder: setting map width = " + width);
            this.width = width;
            return this;
        }

        /**
         * Sets the map height (in tiles).
         *
         * @param height The height.
         * @return This builder instance.
         */
        public Builder setHeight(int height)
        {
            LOGGER.fine("Builder: setting map height = " + height);
            this.height = height;
            return this;
        }

        /**
         * Sets the width of each tile (in pixels).
         *
         * @param tileWidth The tile width.
         * @return This builder instance.
         */
        public Builder setTileWidth(int tileWidth)
        {
            LOGGER.fine("Builder: setting tile width = " + tileWidth);
            this.tileWidth = tileWidth;
            return this;
        }

        /**
         * Sets the height of each tile (in pixels).
         *
         * @param tileHeight The tile height.
         * @return This builder instance.
         */
        public Builder setTileHeight(int tileHeight)
        {
            LOGGER.fine("Builder: setting tile height = " + tileHeight);
            this.tileHeight = tileHeight;
            return this;
        }

        /**
         * Sets the list of tile layers.
         *
         * @param layers The layers to set.
         * @return This builder instance.
         */
        public Builder setLayers(List<TiledLayer> layers)
        {
            LOGGER.info("Builder: setting " + layers.size() + " layer(s)");
            this.layers = new ArrayList<>(layers);
            return this;
        }

        /**
         * Sets the list of tilesets.
         *
         * @param tilesets The tilesets to set.
         * @return This builder instance.
         */
        public Builder setTilesets(List<TiledTileset> tilesets)
        {
            LOGGER.info("Builder: setting " + tilesets.size() + " tileset(s)");
            this.tilesets = new ArrayList<>(tilesets);
            return this;
        }

        /**
         * Sets the list of objects.
         *
         * @param objects The objects to set.
         * @return This builder instance.
         */
        public Builder setObjects(List<TiledObject> objects)
        {
            LOGGER.info("Builder: setting " + objects.size() + " object(s)");
            this.objects = new ArrayList<>(objects);
            return this;
        }

        /**
         * Finalizes and creates a TiledMap instance.
         *
         * @return A new immutable TiledMap.
         */
        public TiledMap build()
        {
            // Validate dimensions before building
            if (width <= 0 || height <= 0)
            {
                LOGGER.warning("Invalid map dimensions: " + width + "x" + height);
            }
            if (tileWidth <= 0 || tileHeight <= 0)
            {
                LOGGER.warning("Invalid tile dimensions: " + tileWidth + "x" + tileHeight);
            }

            LOGGER.info("Building TiledMap...");
            return new TiledMap(this);
        }
    }
}
