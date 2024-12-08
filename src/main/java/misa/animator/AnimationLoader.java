package misa.animator;

import misa.scripting.LuaManager;
import org.luaj.vm2.LuaValue;

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
 * Responsible for loading image arrays into BufferedImage arrays,
 * which can then be utilized for animations in the game engine.
 * Relies on LuaManager for Lua scripting.
 */
@SuppressWarnings("unused")
public class AnimationLoader
{
    private static final Logger LOGGER = Logger.getLogger(AnimationLoader.class.getName());
    private static final ConcurrentHashMap<String, BufferedImage>
            IMAGE_CACHE = new ConcurrentHashMap<>();

    private final LuaManager luaManager;

    /**
     * Constructor to initialize with a LuaManager instance.
     *
     * @param luaManager The LuaManager to handle Lua script execution.
     */
    public AnimationLoader(LuaManager luaManager)
    {
        this.luaManager = luaManager;
    }

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
     * Loads an array of images from a Lua script that defines file paths.
     *
     * @param luaScript A Lua script defining an array of file paths.
     * @return A BufferedImage array containing the loaded images.
     */
    public BufferedImage[] loadAnimationsFromLua(String luaScript)
    {
        LuaValue result = luaManager.loadScript(luaScript);

        if (!result.istable())
        {
            LOGGER.warning("Lua script must return a table of file paths.");
            return new BufferedImage[0];
        }

        List<BufferedImage> images = Stream.of(result.checktable().keys())
                .parallel()
                .map(key -> loadImage(result.get(key).tojstring()))
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
