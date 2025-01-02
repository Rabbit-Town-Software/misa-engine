package misa.entities;

import misa.core.events.gameplay.tiled.TileEnterEvent;
import misa.core.events.gameplay.tiled.TileExitEvent;
import misa.scripting.LuaManager;
import misa.core.events.gameplay.entity.EntitySpawnEvent;
import misa.core.events.gameplay.entity.EntityDestroyEvent;
import misa.core.events.EventManager;

import org.luaj.vm2.LuaValue;

import java.awt.Graphics;
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
    protected BufferedImage currentImage;
    protected LuaManager luaManager; // LuaManager for handling scripting
    protected LuaValue luaScript;   // Lua script defining behavior
    protected static EventManager eventManager = new EventManager();

    /**
     * Constructor for GameObject
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
        eventManager.triggerEvent(new EntitySpawnEvent(this));
    }

    /**
     * Destroys the GameObject and triggers an event.
     */
    public void destroy()
    {
        // Trigger destroy event when GameObject is removed
        eventManager.triggerEvent(new EntityDestroyEvent(this));
        // Additional cleanup (like removing from the world, freeing resources, etc.)
    }

    public void enterTile(int tileX, int tileY)
    {
        // Trigger the TileEnterEvent when the entity enters a tile
        eventManager.triggerEvent(new TileEnterEvent(tileX, tileY));
    }

    public void exitTile(int tileX, int tileY)
    {
        // Trigger the TileExitEvent when the entity exits a tile
        eventManager.triggerEvent(new TileExitEvent(tileX, tileY));
    }

    /**
     * Constructor for GameObject with Lua script
     *
     * @param x          x coordinate
     * @param y          y coordinate
     * @param luaManager LuaManager to handle scripting
     * @param luaScript  Lua script defining behavior
     */
    public GameObject(double x, double y, LuaManager luaManager, String luaScript)
    {
        this(x, y);
        this.luaManager = luaManager;
        this.luaScript = luaManager.loadScript(luaScript);
    }

    /**
     * Draws the GameObject on the screen
     *
     * @param graphics the graphics used for drawing
     */
    public void draw(Graphics graphics)
    {
        if (currentImage != null)
        {
            graphics.drawImage(currentImage, (int) x, (int) y, null);
        }
        else
        {
            LOGGER.warning("There is no image to draw for GameObject.");
        }
    }

    /**
     * Sets the current image to be displayed
     *
     * @param image image to be shown
     */
    public void setCurrentImage(BufferedImage image)
    {
        if (image == null)
        {
            LOGGER.warning("currentImage is null, GameObject might not render correctly.");
        }
        this.currentImage = image;
    }

    /**
     * Updates the GameObject's state using either Java-defined or Lua-defined behavior.
     * Specific behaviors like movement and interactions should be defined here.
     */
    public void update()
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
            // Default behavior for Java-based subclasses
            LOGGER.info("No Lua script defined for update. Extend this class for custom behavior.");
        }
    }

    // Getters and setters below

    /**
     * Sets the new position of the GameObject
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return returns the x coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * @return returns the y coordinate
     */
    public double getY()
    {
        return y;
    }
}
