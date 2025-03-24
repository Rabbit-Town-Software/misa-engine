package misa.systems.animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Responsible for loading image arrays into BufferedImage arrays,
 * which can then be utilized for animations in the game engine.
 */
@SuppressWarnings("unused")
public class AnimationLoader
{
    private static final Logger LOGGER = Logger.getLogger(AnimationLoader.class.getName());
    private static final ConcurrentHashMap<String, BufferedImage>
            IMAGE_CACHE = new ConcurrentHashMap<>();


    /**
     * Loads an array of images from the specified file paths.
     *
     * @param paths An array of file paths pointing to the images to load.
     * @return A BufferedImage array containing the loaded images.
     */
    public static BufferedImage[] loadAnimations(String[] paths)
    {
        List<BufferedImage> images = Stream.of(paths)
                .parallel()
                .map(AnimationLoader::loadImage)
                .toList();
        return images.toArray(new BufferedImage[0]);
    }

    /**
     * Loads an image from the specified file path and caches it to avoid redundant loading.
     *
     * @param path The file path of the image to load.
     * @return The loaded BufferedImage, or null if loading fails.
     */
    private static BufferedImage loadImage(String path)
    {
        if (IMAGE_CACHE.containsKey(path))
        {
            return IMAGE_CACHE.get(path);
        }

        try (InputStream is = AnimationLoader.class.getClassLoader().getResourceAsStream(path))
        {
            if (is == null)
            {
                LOGGER.warning("Image not found: " + path);
                return null;
            }

            BufferedImage image = ImageIO.read(is);
            if (image != null)
            {
                IMAGE_CACHE.put(path, image);
                LOGGER.info("Image loaded and cached: " + path);
            }
            return image;
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to load image: " + path, e);
            return null;
        }
    }
}
