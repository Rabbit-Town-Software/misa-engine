package misa.tiled2misa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * represents a parsed Tiled map with various attributes
 * these attributes include: map size, tile size, layers, tilesets, and objects.
 */
@SuppressWarnings("unused")
public class TiledMap
{
    private static final Logger LOGGER = Logger.getLogger(TiledMap.class.getName());

    // map dimensions
    private final int width, height;
    private final int tileWidth, tileHeight;

    // lists of map components
    private final List<TiledLayer> layers;
    private final List<TiledTileset> tilesets;
    private final List<TiledObject> objects;

    /**
     * private constructor for TiledMap. Use the Builder to create instances
     *
     * @param builder builder object that contains all the data needed for constructing a TiledMap.
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

        LOGGER.info("TiledMap created with dimensions: "
                + width + "x" + height + " and tile size: " + tileWidth + "x" + tileHeight);

        if (layers.isEmpty())
        {
            LOGGER.warning("TiledMap has no layers. This may cause issues during rendering. ");
        }
        if (tilesets.isEmpty())
        {
            LOGGER.warning("TiledMap has no tilesets. Check if the map was parsed correctly. ");
        }
        if (objects.isEmpty())
        {
            LOGGER.info("TiledMap contains no objects. Check if the map was parsed correctly. ");
        }
    }

    // getters for map dimensions
    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    // getters for map components
    public List<TiledLayer> getLayers()
    {
        return layers;
    }

    public List<TiledTileset> getTilesets()
    {
        return tilesets;
    }

    public List<TiledObject> getObjects()
    {
        return objects;
    }

    /**
     * Builder class for creating TiledMap instances
     * use this to set parameters and construct a TiledMap object
     */
    public static class Builder
    {
        private int width, height;
        private int tileWidth, tileHeight;
        private List<TiledLayer> layers = new ArrayList<>();
        private List<TiledTileset> tilesets = new ArrayList<>();
        private List<TiledObject> objects = new ArrayList<>();

        /**
         * sets the map width
         *
         * @param width The width of the map in tiles
         * @return the Builder object for chaining
         */
        public Builder setWidth(int width)
        {
            this.width = width;
            return this;
        }

        /**
         * sets the map height
         *
         * @param height The height of the map in tiles
         * @return the Builder object for chaining
         */
        public Builder setHeight(int height)
        {
            this.height = height;
            return this;
        }

        /**
         * sets the tile width
         *
         * @param tileWidth the width of each tile in pixels
         * @return the Builder object for chaining
         */
        public Builder setTileWidth(int tileWidth)
        {
            this.tileWidth = tileWidth;
            return this;
        }

        /**
         * sets the tile height
         *
         * @param tileHeight the height of each tile in pixels
         * @return the Builder object for chaining
         */
        public Builder setTileHeight(int tileHeight)
        {
            this.tileHeight = tileHeight;
            return this;
        }

        /**
         * sets the layers of the map
         *
         * @param layers the list of TiledLayer objects
         * @return The Builder object for chaining
         */
        public Builder setLayers(List<TiledLayer> layers)
        {
            this.layers = new ArrayList<>(layers);
            return this;
        }

        /**
         * sets the tilesets of the map
         *
         * @param tilesets the list of TiledTileset objects
         * @return the Builder object for chaining
         */
        public Builder setTilesets(List<TiledTileset> tilesets)
        {
            this.tilesets = new ArrayList<>(tilesets);
            return this;
        }

        /**
         * sets the objects present in the map
         *
         * @param objects the list of TiledObject objects
         * @return the Builder object for chaining
         */
        public Builder setObjects(List<TiledObject> objects)
        {
            this.objects = new ArrayList<>(objects);
            return this;
        }

        /**
         * builds and returns the TiledMap instance
         *
         * @return the constructed TiledMap object
         */
        public TiledMap build()
        {
            if (width <= 0 || height <= 0)
            {
                LOGGER.warning("Invalid map dimensions set in Builder: "
                        + width + "x" + height);
            }
            if (tileWidth <= 0 || tileHeight <= 0)
            {
                LOGGER.warning("Invalid tile dimensions set in Builder: "
                        + tileWidth + "x" + tileHeight);
            }
            return new TiledMap(this);
        }
    }
}
