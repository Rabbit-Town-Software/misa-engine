package misa.systems.animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Utility class for loading and caching animation frames.
 *
 * <p>
 * Supports:
 * - Loading single animations (array of images)
 * - Loading named animations (map of names to frame arrays)
 * - Caching images to avoid redundant loading
 * </p>
 */
@SuppressWarnings("unused")
public class AnimationLoader
{
    private static final Logger LOGGER = Logger.getLogger(AnimationLoader.class.getName());

    // Global cache to avoid loading duplicate images
    private static final ConcurrentHashMap<String, BufferedImage> IMAGE_CACHE = new ConcurrentHashMap<>();

    /**
     * Loads a sequence of images for an animation.
     *
     * @param paths Array of resource paths to images.
     * @return An array of loaded BufferedImages.
     */
    public static BufferedImage[] loadAnimations(String[] paths)
    {
        List<BufferedImage> images = Stream.of(paths)
                .parallel() // Parallelize loading to speed up batch loading
                .map(AnimationLoader::loadImage)
                .toList();

        return images.toArray(new BufferedImage[0]);
    }

    /**
     * Loads multiple named animations at once.
     *
     * @param animations Map of animationName -> array of file paths.
     * @return Map of animationName -> array of loaded BufferedImages.
     */
    public static Map<String, BufferedImage[]> loadNamedAnimations(Map<String, String[]> animations)
    {
        Map<String, BufferedImage[]> result = new HashMap<>();

        for (Map.Entry<String, String[]> entry : animations.entrySet())
        {
            String name = entry.getKey();
            String[] paths = entry.getValue();
            result.put(name, loadAnimations(paths));
        }

        return result;
    }

    /**
     * Loads a single image from the classpath or cache.
     *
     * @param path The resource path to the image.
     * @return The loaded BufferedImage, or null if loading failed.
     */
    private static BufferedImage loadImage(String path)
    {
        // Return cached image if available
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
