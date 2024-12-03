package misa.tiled2misa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a parsed Tiled map with attributes such as map size, tile size, layers, tilesets, and objects.
 */
@SuppressWarnings("unused")
public class TiledMap
{
    private static final Logger LOGGER = Logger.getLogger(TiledMap.class.getName());

    private final int width, height;
    private final int tileWidth, tileHeight;
    private final List<TiledLayer> layers;
    private final List<TiledTileset> tilesets;
    private final List<TiledObject> objects;

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
            LOGGER.warning("TiledMap has no layers. This may cause rendering issues.");
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

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    public List<TiledLayer> getLayers() { return layers; }
    public List<TiledTileset> getTilesets() { return tilesets; }
    public List<TiledObject> getObjects() { return objects; }

    /**
     * Builder class for creating TiledMap instances.
     */
    public static class Builder
    {
        private int width, height;
        private int tileWidth, tileHeight;
        private List<TiledLayer> layers = new ArrayList<>();
        private List<TiledTileset> tilesets = new ArrayList<>();
        private List<TiledObject> objects = new ArrayList<>();

        public Builder setWidth(int width) { this.width = width; return this; }
        public Builder setHeight(int height) { this.height = height; return this; }
        public Builder setTileWidth(int tileWidth) { this.tileWidth = tileWidth; return this; }
        public Builder setTileHeight(int tileHeight) { this.tileHeight = tileHeight; return this; }
        public Builder setLayers(List<TiledLayer> layers) { this.layers = new ArrayList<>(layers); return this; }
        public Builder setTilesets(List<TiledTileset> tilesets) { this.tilesets = new ArrayList<>(tilesets); return this; }
        public Builder setObjects(List<TiledObject> objects) { this.objects = new ArrayList<>(objects); return this; }

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
