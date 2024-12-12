package misa.data.tiled2misa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents a tileset in a Tiled map.
 * <p>
 * - `source`: File path to the tileset image (e.g., "tileset.png").
 * - `firstGID`: The first Global Tile ID associated with this tileset.
 */
@SuppressWarnings("unused")
public class TiledTileset
{
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
        this.image = loadImage(source);  // Load the image when the tileset is created
    }

    /**
     * Loads the image from the specified source path.
     *
     * @param source The path to the image file.
     * @return The loaded image, or null if the image can't be loaded.
     */
    private Image loadImage(String source)
    {
        try
        {
            return ImageIO.read(new File(source));  // Load the image from the file
        }
        catch (IOException e)
        {
            e.printStackTrace();  // Handle the error gracefully
        }
        return null;  // Return null if the image can't be loaded
    }

    /**
     * Checks if a given tile ID belongs to this tileset.
     *
     * @param tileID The tile ID to check.
     * @return True if the tile ID belongs to this tileset, false otherwise.
     */
    public boolean containsTile(int tileID)
    {
        return tileID >= firstGID;
    }

    // Getter for the image
    public Image getImage()
    {
        return image;
    }

    // Getter for the source
    public String getSource()
    {
        return source;
    }

    // Getter for the firstGID
    public int firstGID()
    {
        return firstGID;
    }
}
