package misa.scripting;

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
    private final Map<String, String> eventScripts = new HashMap<>(); // Maps event names to script filenames

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
    public void registerEventScript(String eventName, String script)
    {
        eventScripts.put(eventName, script);
        LOGGER.info("Registered Lua script for event: " + eventName);
    }

    public void registerEventScriptFromFile(String eventName, String filePath)
    {
        String scriptContent = luaManager.loadScriptFromFile(filePath);
        if (scriptContent != null && !scriptContent.isEmpty())
        {
            registerEventScript(eventName, scriptContent);
        }
        else
        {
            LOGGER.warning("Failed to load Lua script from file: " + filePath);
        }
    }

    /**
     * Triggers a registered Lua script by event name.
     *
     * @param eventName The name of the event.
     */
    public void triggerEvent(String eventName)
    {
        String script = eventScripts.get(eventName);
        if (script == null)
        {
            LOGGER.warning("No Lua script registered for event: " + eventName);
            return;
        }

        try
        {
            luaManager.executeScript(script);
            LOGGER.info("Executed Lua script for event: " + eventName);
        }
        catch (Exception e)
        {
            LOGGER.severe("Error executing Lua script for event: " + eventName + ": " + e.getMessage());
        }
    }
}
