package misa.animator;

import misa.entities.GameObject;
import misa.scripting.LuaManager;
import org.luaj.vm2.LuaValue;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Handles the playback of animations by sequencing through frames of
 * BufferedImage arrays and applying them to game objects.
 * Supports Lua scripting for controlling animations.
 * Integrates with Graphics2D rendering loop for rendering animations.
 */
@SuppressWarnings("unused")
public class Animator
{
    private static final Logger LOGGER = Logger.getLogger(Animator.class.getName());

    private final LuaManager luaManager;
    private int currentFrame = 0;     // The current frame index being displayed
    private long lastFrameTime = 0;   // Timestamp of the last frame update
    private long frameDuration = 200; // Default duration for each frame in milliseconds

    /**
     * Constructor to initialize with a LuaManager instance.
     *
     * @param luaManager The LuaManager to handle Lua script execution.
     */
    public Animator(LuaManager luaManager)
    {
        this.luaManager = luaManager;
    }

    /**
     * Animates the specified GameObject using the given frames.
     *
     * @param animationFrames An array of BufferedImages representing animation frames.
     * @param shouldLoop      A flag indicating whether the animation should loop.
     * @param gameObject      The GameObject to which the animation will be applied.
     * @param graphics        The Graphics2D context for rendering.
     */
    public void animate(BufferedImage[] animationFrames, boolean shouldLoop, GameObject gameObject, Graphics2D graphics)
    {
        if (animationFrames == null || animationFrames.length == 0)
        {
            LOGGER.warning("Animation frames are empty or null. Animation skipped.");
            return;
        }

        long currentTime = System.nanoTime();
        if (shouldUpdateFrame(currentTime))
        {
            updateCurrentFrame(animationFrames, shouldLoop);
            lastFrameTime = currentTime;
        }

        renderCurrentFrame(animationFrames, gameObject, graphics);
    }

    /**
     * Animates using parameters specified in a Lua script.
     *
     * @param luaScript A Lua script defining animation parameters:
     *                  - frames: Array of file paths.
     *                  - loop: Boolean indicating whether to loop the animation.
     *                  - frameDuration: Frame duration in milliseconds.
     * @param gameObject The GameObject to animate.
     * @param graphics   The Graphics2D context for rendering.
     */
    public void animateFromLua(String luaScript, GameObject gameObject, Graphics2D graphics)
    {
        LuaValue config = luaManager.loadScript(luaScript);

        if (!config.istable())
        {
            LOGGER.warning("Lua script must return a table with animation parameters.");
            return;
        }

        String[] paths = Stream.of(config.get("frames").checktable().keys())
                .map(key -> config.get("frames").get(key).tojstring())
                .toArray(String[]::new);

        boolean loop = config.get("loop").toboolean();
        long duration = config.get("frameDuration").tolong();

        setFrameDuration(duration);
        BufferedImage[] animationFrames = AnimationLoader.loadAnimations(paths);
        animate(animationFrames, loop, gameObject, graphics);
    }

    /**
     * Determines whether the frame should be updated based on elapsed time.
     *
     * @param currentTime The current timestamp in nanoseconds.
     * @return True if enough time has passed to update the frame, otherwise false.
     */
    private boolean shouldUpdateFrame(long currentTime)
    {
        long elapsedTime = (currentTime - lastFrameTime) / 1_000_000; // Convert to milliseconds
        return elapsedTime > frameDuration;
    }

    /**
     * Updates the current frame index, restarting the animation if looping is enabled.
     *
     * @param animationFrames The array of animation frames.
     * @param shouldLoop      A flag indicating whether the animation should loop.
     */
    private void updateCurrentFrame(BufferedImage[] animationFrames, boolean shouldLoop)
    {
        currentFrame++;
        if (currentFrame >= animationFrames.length)
        {
            if (shouldLoop)
            {
                LOGGER.info("Animation looped successfully.");
                currentFrame = 0;
            }
            else
            {
                LOGGER.info("Animation completed.");
                currentFrame = animationFrames.length - 1; // Stay on the last frame
            }
        }
    }

    /**
     * Renders the current animation frame to the Graphics2D context.
     *
     * @param animationFrames The array of animation frames.
     * @param gameObject      The GameObject to render.
     * @param graphics        The Graphics2D context for rendering.
     */
    private void renderCurrentFrame(BufferedImage[] animationFrames, GameObject gameObject, Graphics2D graphics)
    {
        BufferedImage currentFrameImage = animationFrames[currentFrame];
        graphics.drawImage(currentFrameImage, (int) gameObject.getX(), (int) gameObject.getY(), null);
    }

    /**
     * Sets the duration for each frame in the animation.
     *
     * @param duration The duration in milliseconds. Must be greater than 0.
     */
    public void setFrameDuration(long duration)
    {
        if (duration <= 0)
        {
            LOGGER.warning("Invalid frame duration. Resetting to default value of 200ms.");
            this.frameDuration = 200;
        }
        else
        {
            this.frameDuration = duration;
            LOGGER.info("Frame duration set to: " + duration + "ms");
        }
    }
}
