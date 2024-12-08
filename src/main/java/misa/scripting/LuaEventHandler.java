package misa.scripting;

import org.luaj.vm2.LuaValue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Handles registration and execution of Lua-based event scripts.
 * Supports associating Lua scripts with custom events and triggering them with arguments.
 */
@SuppressWarnings("unused")
public class LuaEventHandler
{
    private static final Logger LOGGER = Logger.getLogger(LuaEventHandler.class.getName());
    private final LuaManager luaManager; // Reference to the LuaManager for script execution
    private final Map<String, LuaValue> eventHandlers = new HashMap<>(); // Registered event handlers

    /**
     * Constructs a LuaEventHandler.
     *
     * @param luaManager The LuaManager to use for script execution.
     */
    public LuaEventHandler(LuaManager luaManager)
    {
        this.luaManager = luaManager;
    }

    /**
     * Registers a Lua script as an event handler.
     *
     * @param eventName The name of the event.
     * @param script    The Lua script defining the event handler.
     */
    public void registerEventHandler(String eventName, String script)
    {
        LuaValue handler = luaManager.loadScript(script);
        if (!handler.isnil())
        {
            eventHandlers.put(eventName, handler);
            LOGGER.info("Registered Lua event handler for event: " + eventName);
        }
        else
        {
            LOGGER.warning("Failed to register event handler for event: " + eventName);
        }
    }

    /**
     * Triggers an event, executing the associated Lua script.
     *
     * @param eventName The name of the event.
     * @param args      The arguments to pass to the Lua script.
     */
    public void triggerEvent(String eventName, LuaValue... args)
    {
        LuaValue handler = eventHandlers.get(eventName);
        if (handler == null)
        {
            LOGGER.warning("No handler registered for event: " + eventName);
            return;
        }
        try
        {
            handler.invoke(args);
        }
        catch (Exception e)
        {
            LOGGER.severe("Error executing Lua script for event: " + eventName + ": " + e.getMessage());
        }
    }
}
