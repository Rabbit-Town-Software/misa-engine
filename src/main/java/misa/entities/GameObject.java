package misa.entities;

import misa.core.events.gameplay.tiled.TileEnterEvent;
import misa.core.events.gameplay.tiled.TileExitEvent;
import misa.core.events.gameplay.entity.EntitySpawnEvent;
import misa.core.events.gameplay.entity.EntityDestroyEvent;
import misa.core.events.EventManager;

import misa.systems.animation.Animator;
import misa.systems.animation.AnimationLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This class is made to be extended by specific game entities like players, enemies, NPCs, etc.
 * It has basic position, rendering properties, and supports Lua scripting for dynamic behaviors.
 */
@SuppressWarnings("unused")
public abstract class GameObject
{
    private static final Logger LOGGER = Logger.getLogger(GameObject.class.getName());

    protected double coordinateX, coordinateY; // coordinates

    protected BufferedImage[] animationFrames; // Array of animation frames
    protected Animator animator; // Current frame index
    protected static EventManager eventManager;

    protected boolean shouldAnimate;
    protected boolean shouldLoop;

    /**
     * Constructor for GameObject
     *
     * @param coordinateX             x coordinate
     * @param coordinateY             y coordinate
     * @param shouldAnimate           boolean for whether it should animate or not
     * @param shouldLoop              boolean for whether it should loop or not
     */
    public GameObject(double coordinateX, double coordinateY,
                      boolean shouldAnimate, boolean shouldLoop,
                      BufferedImage[] animationFrames)
    {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.shouldAnimate = shouldAnimate;
        this.shouldLoop = shouldLoop;
        this.animator = new Animator();
        this.animationFrames = animationFrames;
        eventManager.triggerEvent(new EntitySpawnEvent(this));
    }

    /**
     * Loads animation frames from file paths.
     *
     * @param paths Array of file paths for animation frames.
     * @return return the animation arrays.
     */
    public BufferedImage[] loadAnimations(String[] paths)
    {
        return AnimationLoader.loadAnimations(paths);
    }



    /**
     * Animates the GameObject using the loaded frames.
     *
     * @param graphics Graphics2D context for rendering.
     * @param shouldLoop Whether the animation should loop.
     */
    public void animate(BufferedImage[] animationFrames, Graphics2D graphics, boolean shouldLoop)
    {
        if (animationFrames != null)
        {
            animator.animate(animationFrames, shouldLoop, this, graphics);
        }
        else
        {
            LOGGER.warning("No animation frames loaded for GameObject");
        }
    }

    /**
     * Draws the GameObject on the screen
     *
     * @param graphics the graphics used for drawing
     */
    public void draw(Graphics graphics)
    {
        if (graphics instanceof Graphics2D graphics2D)
        {
            if (shouldAnimate)
            {
                animate(animationFrames, graphics2D, shouldLoop);
            }
            else
            {
                LOGGER.warning("Shouldn't animate. Add in static art capability Case, seriously.......!!!!!");
                LOGGER.warning("Static fallback — drawing rectangle.");
                graphics.setColor(Color.RED);
                graphics.fillRect((int) coordinateX, (int) coordinateY, 72, 72);
            }
        }
        else
        {
            LOGGER.warning("Graphics instance is not Graphics2D.");
        }
    }

    /**
     * Destroys the GameObject and triggers an event.
     */
    public void destroy()
    {
        eventManager.triggerEvent(new EntityDestroyEvent(this));
    }

    public void enterTile(int tileX, int tileY)
    {
        eventManager.triggerEvent(new TileEnterEvent(tileX, tileY));
    }

    public void exitTile(int tileX, int tileY)
    {
        eventManager.triggerEvent(new TileExitEvent(tileX, tileY));
    }




    // Getters and setters for position
    public double getCoordinateX() { return coordinateX; }
    public double getCoordinateY() { return coordinateY; }

    public void setPosition(double coordinateX, double coordinateY)
    {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Static method to set the EventManager.
     *
     * @param manager the EventManager to set
     */
    public static void setEventManager(EventManager manager)
    {
        eventManager = manager;
    }

    public void setShouldAnimate(boolean value) { this.shouldAnimate = value; }
    public void setShouldLoop(boolean value) { this.shouldLoop = value; }

    public void setAnimationFrames(BufferedImage[] animationFrames)
    {
        this.animationFrames = animationFrames;
        LOGGER.warning("Animation frames set to " + Arrays.toString(animationFrames));
    }
}
