package misa.data.tiled2misa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a parsed Tiled map.
 */
@SuppressWarnings("unused")
public class TiledMap
{
    private static final Logger LOGGER = Logger.getLogger(TiledMap.class.getName());

    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
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

        LOGGER.info("✅ TiledMap constructed:");
        LOGGER.info(" - Map Size: " + width + " x " + height + " tiles");
        LOGGER.info(" - Tile Size: " + tileWidth + " x " + tileHeight + " pixels");
        LOGGER.info(" - Layers: " + layers.size());
        LOGGER.info(" - Tilesets: " + tilesets.size());
        LOGGER.info(" - Objects: " + objects.size());

        if (layers.isEmpty())
        {
            LOGGER.warning("⚠️ TiledMap has no layers. The map will not render any tiles.");
        }

        if (tilesets.isEmpty())
        {
            LOGGER.warning("⚠️ TiledMap has no tilesets. Check if TSX was loaded and parsed correctly.");
        }

        if (objects.isEmpty())
        {
            LOGGER.info("ℹ️ TiledMap has no objects. That's okay if none were defined.");
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    public List<TiledLayer> getLayers() { return layers; }
    public List<TiledTileset> getTilesets() { return tilesets; }
    public List<TiledObject> getObjects() { return objects; }

    public static class Builder
    {
        private int width, height;
        private int tileWidth, tileHeight;
        private List<TiledLayer> layers = new ArrayList<>();
        private List<TiledTileset> tilesets = new ArrayList<>();
        private List<TiledObject> objects = new ArrayList<>();

        public Builder setWidth(int width)
        {
            LOGGER.fine("Builder: setting map width = " + width);
            this.width = width;
            return this;
        }

        public Builder setHeight(int height)
        {
            LOGGER.fine("Builder: setting map height = " + height);
            this.height = height;
            return this;
        }

        public Builder setTileWidth(int tileWidth)
        {
            LOGGER.fine("Builder: setting tile width = " + tileWidth);
            this.tileWidth = tileWidth;
            return this;
        }

        public Builder setTileHeight(int tileHeight)
        {
            LOGGER.fine("Builder: setting tile height = " + tileHeight);
            this.tileHeight = tileHeight;
            return this;
        }

        public Builder setLayers(List<TiledLayer> layers)
        {
            LOGGER.info("Builder: setting " + layers.size() + " layer(s)");
            this.layers = new ArrayList<>(layers);
            return this;
        }

        public Builder setTilesets(List<TiledTileset> tilesets)
        {
            LOGGER.info("Builder: setting " + tilesets.size() + " tileset(s)");
            this.tilesets = new ArrayList<>(tilesets);
            return this;
        }

        public Builder setObjects(List<TiledObject> objects)
        {
            LOGGER.info("Builder: setting " + objects.size() + " object(s)");
            this.objects = new ArrayList<>(objects);
            return this;
        }

        public TiledMap build()
        {
            if (width <= 0 || height <= 0)
            {
                LOGGER.warning("🚫 Invalid map dimensions: " + width + "x" + height);
            }
            if (tileWidth <= 0 || tileHeight <= 0)
            {
                LOGGER.warning("🚫 Invalid tile dimensions: " + tileWidth + "x" + tileHeight);
            }

            LOGGER.info("🚀 Building TiledMap...");
            return new TiledMap(this);
        }
    }
}
