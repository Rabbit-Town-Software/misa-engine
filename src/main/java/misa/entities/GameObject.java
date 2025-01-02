package misa.entities;

import misa.core.events.gameplay.tiled.TileEnterEvent;
import misa.core.events.gameplay.tiled.TileExitEvent;
import misa.scripting.LuaManager;
import misa.scripting.LuaEventHandler;
import misa.core.events.gameplay.entity.EntitySpawnEvent;
import misa.core.events.gameplay.entity.EntityDestroyEvent;
import misa.core.events.EventManager;

import misa.systems.animation.Animator;
import misa.systems.animation.AnimationLoader;
import org.luaj.vm2.LuaValue;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * This class is made to be extended by specific game entities like players, enemies, NPCs, etc.
 * It has basic position, rendering properties, and supports Lua scripting for dynamic behaviors.
 */
@SuppressWarnings("unused")
public abstract class GameObject
{
    private static final Logger LOGGER = Logger.getLogger(GameObject.class.getName());

    protected double x, y; // coordinates

    protected BufferedImage[] animationFrames; // Array of animation frames
    protected Animator animator; // Current frame index

    protected LuaManager luaManager; // LuaManager for handling scripting
    protected LuaEventHandler luaEventHandler;
    protected LuaValue luaScript; // Lua script defining behavior
    protected static EventManager eventManager;

    /**
     * Constructor for GameObject
     *
     * @param x             x coordinate
     * @param y             y coordinate
     * @param luaManager    LuaManager for scripting
     * @param luaEventHandler LuaEventHandler for handling Lua-based events
     */
    public GameObject(double x, double y, LuaManager luaManager, LuaEventHandler luaEventHandler)
    {
        this.x = x;
        this.y = y;
        this.luaManager = luaManager;
        this.luaEventHandler = luaEventHandler;
        this.animator = new Animator(luaManager);
        eventManager.triggerEvent(new EntitySpawnEvent(this));
    }

    /**
     * Loads animation frames from file paths.
     *
     * @param paths Array of file paths for animation frames.
     */
    public void loadAnimationFromPaths(String[] paths)
    {
        this.animationFrames = AnimationLoader.loadAnimations(paths);
    }

    /**
     * Loads animation frames from a Lua script.
     *
     * @param luaScript Lua script defining file paths.
     */
    public void loadAnimationFromLua(String luaScript)
    {
        this.animationFrames = new AnimationLoader(luaManager).loadAnimationsFromLua(luaScript);
    }

    /**
     * Animates the GameObject using the loaded frames.
     *
     * @param graphics Graphics2D context for rendering.
     * @param shouldLoop Whether the animation should loop.
     */
    public void animate(Graphics2D graphics, boolean shouldLoop)
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
     * Load a Lua script for this GameObject.
     *
     * @param luaScriptPath Path to the Lua script
     */
    public void loadLuaScript(String luaScriptPath)
    {
        if (luaManager != null)
        {
            this.luaScript = luaManager.loadScript(luaScriptPath);
        }
        else
        {
            LOGGER.warning("LuaManager is not initialized. Cannot load script.");
        }
    }

    /**
     * Executes the Lua script, if available.
     */
    public void executeLuaScript()
    {
        if (luaScript != null && luaScript.isfunction())
        {
            try
            {
                luaScript.call();
            }
            catch (Exception e)
            {
                LOGGER.severe("Error executing Lua script for GameObject: " + e.getMessage());
            }
        }
        else
        {
            LOGGER.fine("No Lua script or callable function available for execution.");
        }
    }

    /**
     * Updates the GameObject's state. Can be overridden by subclasses for Java-defined behavior.
     */
    public void update()
    {
        executeLuaScript(); // Optional Lua-based behavior
    }

    /**
     * Draws the GameObject on the screen
     *
     * @param graphics the graphics used for drawing
     */
    public void draw(Graphics graphics)
    {
        if (graphics instanceof Graphics2D)
        {
            animate((Graphics2D) graphics, true);
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
    public double getX() { return x; }
    public double getY() { return y; }

    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
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
}
