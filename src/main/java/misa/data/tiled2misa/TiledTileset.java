package misa.data.tiled2misa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents a tileset in a Tiled map.
 *
 * <p>
 * Each TiledTileset links a tile image to a first global ID (firstGID),
 * allowing maps to look up which tileset an individual tile belongs to.
 * </p>
 */
@SuppressWarnings("unused")
public class TiledTileset
{
    private static final Logger LOGGER = Logger.getLogger(TiledTileset.class.getName());

    private final String source;  // File path to the tileset image
    private final int firstGID;   // First Global Tile ID for this tileset
    private final Image image;    // The loaded tileset image

    /**
     * Constructor to initialize the TiledTileset with a source path and firstGID.
     *
     * @param source The file path to the tileset image.
     * @param firstGID The first Global Tile ID associated with the tileset.
     */
    public TiledTileset(String source, int firstGID)
    {
        this.source = source;
        this.firstGID = firstGID;
        this.image = loadImage(source);

        // Log success or failure of loading
        if (image != null)
        {
            LOGGER.info("Tileset image loaded: " + source);
        }
        else
        {
            LOGGER.severe("Failed to load tileset image: " + source + " (Check file path and format)");
        }
    }

    /**
     * Loads the image from the specified source path.
     *
     * @param source The path to the image file.
     * @return The loaded Image, or null if loading fails.
     */
    private Image loadImage(String source)
    {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(source))
        {
            if (stream == null)
            {
                LOGGER.severe("Image resource not found on classpath: " + source);
                return null;
            }

            Image img = ImageIO.read(stream);

            if (img == null)
            {
                LOGGER.warning("ImageIO.read returned null — unsupported format or corrupted image: " + source);
            }

            return img;
        }
        catch (IOException e)
        {
            LOGGER.severe("IOException while loading image resource: " + source);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if this tileset contains the given global tile ID.
     *
     * @param tileID Global tile ID to check.
     * @return True if this tileset covers that tile ID.
     */
    public boolean containsTile(int tileID)
    {
        return tileID >= firstGID;
    }

    /**
     * @return The loaded tileset image.
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * @return The original source path for the image.
     */
    public String getSource()
    {
        return source;
    }

    /**
     * @return The first global tile ID for this tileset.
     */
    public int firstGID()
    {
        return firstGID;
    }
}
