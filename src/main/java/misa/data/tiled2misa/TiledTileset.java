package misa.data.tiled2misa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents a tileset in a Tiled map.
 */
@SuppressWarnings("unused")
public class TiledTileset
{
    private static final Logger LOGGER = Logger.getLogger(TiledTileset.class.getName());

    private final String source;  // File path to the tileset image
    private final int firstGID;   // First Global Tile ID
    private final Image image;    // Image of the tileset

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

        if (image != null)
        {
            LOGGER.info("✅ Tileset image loaded: " + source);
        }
        else
        {
            LOGGER.severe("❌ Failed to load tileset image: " + source + " (Check file path and format)");
        }
    }

    /**
     * Loads the image from the specified source path.
     *
     * @param source The path to the image file.
     * @return The loaded image, or null if the image can't be loaded.
     */
    private Image loadImage(String source)
    {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(source))
        {
            if (stream == null)
            {
                LOGGER.severe("📛 Image resource not found on classpath: " + source);
                return null;
            }

            Image img = ImageIO.read(stream);
            if (img == null)
            {
                LOGGER.warning("⚠️ ImageIO.read returned null — unsupported format or corrupted image: " + source);
            }

            return img;
        }
        catch (IOException e)
        {
            LOGGER.severe("💥 IOException while loading image resource: " + source);
            e.printStackTrace();
            return null;
        }
    }


    public boolean containsTile(int tileID)
    {
        return tileID >= firstGID;
    }

    public Image getImage()
    {
        return image;
    }

    public String getSource()
    {
        return source;
    }

    public int firstGID()
    {
        return firstGID;
    }
}
