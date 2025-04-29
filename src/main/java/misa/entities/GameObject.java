package misa.entities;

import misa.core.events.EventManager;
import misa.core.events.gameplay.entity.EntityDestroyEvent;
import misa.core.events.gameplay.entity.EntitySpawnEvent;
import misa.core.events.gameplay.tiled.TileEnterEvent;
import misa.core.events.gameplay.tiled.TileExitEvent;
import misa.systems.animation.AnimationLoader;
import misa.systems.animation.Animator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Base class for any entity that exists in the world.
 * <p>
 * Handles:
 * - Position (coordinateX, coordinateY)
 * - Animation (via Animator and loaded frames)
 * - Event system integration (spawn, destroy, tile enter/exit)
 * <p>
 * Extend this class to create game-specific objects.
 */
@SuppressWarnings("unused")
public abstract class GameObject
{
    private static final Logger LOGGER = Logger.getLogger(GameObject.class.getName());

    // World position (in units)
    public double coordinateX;
    public double coordinateY;

    // Event system
    protected static EventManager eventManager;

    // Animation system
    protected Map<String, BufferedImage[]> namedAnimations = new HashMap<>();
    protected BufferedImage[] currentAnimationFrames;
    protected Animator animator = new Animator();
    protected boolean shouldAnimate;
    protected boolean shouldLoop;

    /**
     * Creates a new GameObject.
     *
     * @param coordinateX Starting X position (units)
     * @param coordinateY Starting Y position (units)
     * @param shouldAnimate Whether the object should animate
     * @param shouldLoop Whether the animation should loop
     * @param initialFrames Initial animation frames (can be null)
     */
    public GameObject(
            double coordinateX,
            double coordinateY,
            boolean shouldAnimate,
            boolean shouldLoop,
            BufferedImage[] initialFrames
    )
    {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.shouldAnimate = shouldAnimate;
        this.shouldLoop = shouldLoop;
        this.currentAnimationFrames = initialFrames;

        if (eventManager != null)
        {
            eventManager.triggerEvent(new EntitySpawnEvent(this));
        }
    }

    /**
     * Sets named animations for this GameObject.
     *
     * @param animations Map of animation names to frame arrays.
     */
    public void setNamedAnimations(Map<String, BufferedImage[]> animations)
    {
        this.namedAnimations = animations;
    }

    /**
     * Switches the current animation to a named animation.
     *
     * @param animationName Name of the animation to play.
     */
    public void playAnimation(String animationName)
    {
        if (namedAnimations.containsKey(animationName))
        {
            currentAnimationFrames = namedAnimations.get(animationName);
        }
        else
        {
            LOGGER.warning("Animation not found: " + animationName);
        }
    }

    /**
     * Utility function to load animation frames from resource paths.
     *
     * @param paths Array of resource paths to frame images.
     * @return Array of loaded BufferedImages.
     */
    public BufferedImage[] loadAnimations(String[] paths)
    {
        return AnimationLoader.loadAnimations(paths);
    }

    /**
     * Draws the GameObject applying a camera/world offset.
     *
     * @param graphics2D Graphics2D context.
     * @param offsetX Horizontal camera offset.
     * @param offsetY Vertical camera offset.
     */
    public void drawTranslated(Graphics2D graphics2D, float offsetX, float offsetY)
    {
        if (shouldAnimate && currentAnimationFrames != null)
        {
            animator.animate(
                    currentAnimationFrames,
                    shouldLoop,
                    this,
                    graphics2D,
                    offsetX,
                    offsetY
            );
        }
        else
        {
            graphics2D.setColor(Color.RED);
            graphics2D.fillRect(
                    (int)(coordinateX + offsetX),
                    (int)(coordinateY + offsetY),
                    1, 1
            );
        }
    }

    /**
     * Triggers a destruction event for this GameObject.
     */
    public void destroy()
    {
        if (eventManager != null)
        {
            eventManager.triggerEvent(new EntityDestroyEvent(this));
        }
    }

    /**
     * Notifies that this GameObject has entered a tile.
     *
     * @param tileX Tile X coordinate.
     * @param tileY Tile Y coordinate.
     */
    public void enterTile(int tileX, int tileY)
    {
        if (eventManager != null)
        {
            eventManager.triggerEvent(new TileEnterEvent(tileX, tileY));
        }
    }

    /**
     * Notifies that this GameObject has exited a tile.
     *
     * @param tileX Tile X coordinate.
     * @param tileY Tile Y coordinate.
     */
    public void exitTile(int tileX, int tileY)
    {
        if (eventManager != null)
        {
            eventManager.triggerEvent(new TileExitEvent(tileX, tileY));
        }
    }

    // ----------- Position Getters/Setters -----------

    /**
     * @return The GameObject's X coordinate (units).
     */
    public double getCoordinateX()
    {
        return coordinateX;
    }

    /**
     * @return The GameObject's Y coordinate (units).
     */
    public double getCoordinateY()
    {
        return coordinateY;
    }

    /**
     * Sets the GameObject's position.
     *
     * @param coordinateX New X coordinate (units).
     * @param coordinateY New Y coordinate (units).
     */
    public void setPosition(double coordinateX, double coordinateY)
    {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    // ----------- Event Manager Setter -----------

    /**
     * Sets the global EventManager for all GameObjects.
     *
     * @param manager The EventManager instance.
     */
    public static void setEventManager(EventManager manager)
    {
        eventManager = manager;
    }

    // ----------- Animation Controls -----------

    /**
     * Enables or disables animation playback.
     *
     * @param value True to enable animation; false to disable.
     */
    public void setShouldAnimate(boolean value)
    {
        this.shouldAnimate = value;
    }

    /**
     * Enables or disables animation looping.
     *
     * @param value True to loop animation; false to play once.
     */
    public void setShouldLoop(boolean value)
    {
        this.shouldLoop = value;
    }

    // ----------- Game Loop Methods -----------

    /**
     * Per-frame update method.
     * <p>
     * Override this method to define GameObject-specific update behavior.
     */
    public void update()
    {
        // No default behavior
    }

    /**
     * Draws the GameObject at a specific pixel location.
     *
     * @param graphics2D Graphics2D context.
     * @param px Pixel X coordinate.
     * @param py Pixel Y coordinate.
     * @param sizeInPixels Size of the rendered object in pixels.
     */
    public void drawAtPixel(Graphics2D graphics2D, int px, int py, int sizeInPixels)
    {
        if (shouldAnimate && currentAnimationFrames != null)
        {
            animator.animateAtPixel(
                    currentAnimationFrames,
                    shouldLoop,
                    this,
                    graphics2D,
                    px,
                    py,
                    sizeInPixels
            );
        }
        else
        {
            graphics2D.setColor(Color.RED);
            graphics2D.fillRect(px, py, sizeInPixels, sizeInPixels);
        }
    }
}
