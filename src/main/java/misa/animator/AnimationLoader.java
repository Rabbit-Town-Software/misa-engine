package misa.animator;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * this class is responsible for loading arrays of images into BufferedImage arrays
 * these BufferedImage arrays can then be used in animation
 */
@SuppressWarnings("unused")
public class AnimationLoader
{
    private static final Logger LOGGER = Logger.getLogger(AnimationLoader.class.getName());

    // cache to store all loaded images in order to prevent duplicate loading
    private static final ConcurrentHashMap<String, BufferedImage> imageCache = new ConcurrentHashMap<>();

    /**
     * returns an array of images which can then be used in the animator
     *
     * @param paths array of file paths from where to load images
     * @return returns an array of images which can be used in the animator
     */
    public static BufferedImage[] loadAnimations(String[] paths)
    {
        // uses parallel streams to load the images at the same time for better performance.
        List<BufferedImage> images = Stream.of(paths)
                .parallel()
                .map(AnimationLoader::loadImage)
                .toList();
        return images.toArray(new BufferedImage[0]);
    }

    /**
     * loads images from the path and uses caching to avoid loading the same image multiple times
     *
     * @param path the file path for the image
     * @return the image, or null if the image failed to load
     */
    private static BufferedImage loadImage(String path)
    {
        // check if the image is already cached
        if (imageCache.containsKey(path))
        {
            return imageCache.get(path);
        }

        // load the image
        try (InputStream is = AnimationLoader.class.getClassLoader().getResourceAsStream(path))
        {
            if (is == null)
            {
                LOGGER.warning("Image not found: " + path);
                return null;
            }

            // read image from input stream and cache it
            BufferedImage image = ImageIO.read(is);
            if (image != null)
            {
                imageCache.put(path, image);
                LOGGER.info("Image loaded: " + path);
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
