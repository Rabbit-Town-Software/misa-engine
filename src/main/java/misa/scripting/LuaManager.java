package misa.scripting;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Manages the Lua scripting environment and provides utilities for executing Lua scripts.
 * Provides access to the global Lua environment and supports preloading reusable scripts.
 */
@SuppressWarnings("unused")
public class LuaManager
{
    private static final Logger LOGGER = Logger.getLogger(LuaManager.class.getName());
    private final LuaValue globals; // Global Lua environment
    private final Map<String, LuaValue> preloadedScripts = new HashMap<>(); // Cache for reusable scripts

    /**
     * Initializes the Lua scripting environment.
     */
    public LuaManager()
    {
        globals = JsePlatform.standardGlobals();
    }

    /**
     * Returns the global Lua environment.
     *
     * @return The global Lua environment.
     */
    public LuaValue getGlobals()
    {
        return globals;
    }

    /**
     * Loads a Lua script from a string.
     *
     * @param script The Lua script as a string.
     * @return The compiled script as a LuaValue, or NIL if an error occurs.
     */
    public LuaValue loadScript(String script)
    {
        try
        {
            return globals.get("load").call(LuaValue.valueOf(script));
        }
        catch (Exception e)
        {
            LOGGER.severe("Failed to load Lua script: " + e.getMessage());
            return LuaValue.NIL;
        }
    }

    /**
     * Loads a Lua script from a file and returns its content as a string.
     *
     * @param filePath The path to the Lua script file.
     * @return The content of the Lua script, or null if an error occurs.
     */
    public String loadScriptFromFile(String filePath)
    {
        try
        {
            return Files.readString(Paths.get(filePath));
        }
        catch (IOException e)
        {
            LOGGER.severe("Failed to load Lua script from file: " + filePath + ". Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Executes a Lua script and returns its result.
     *
     * @param script The Lua script as a string.
     */
    public void executeScript(String script)
    {
        try
        {
            LuaValue chunk = loadScript(script);
            chunk.call();
        }
        catch (Exception e)
        {
            LOGGER.severe("Failed to execute Lua script: " + e.getMessage());
        }
    }

    /**
     * Preloads a Lua script for reuse.
     *
     * @param name   The name of the script.
     * @param script The Lua script as a string.
     */
    public void preloadScript(String name, String script)
    {
        LuaValue chunk = loadScript(script);

        if (!chunk.isnil())
        {
            preloadedScripts.put(name, chunk);
        }
        else
        {
            LOGGER.warning("Failed to preload Lua script: " + name);
        }
    }

    /**
     * Retrieves a preloaded script by name.
     *
     * @param name The name of the script.
     * @return The preloaded LuaValue, or NIL if not found.
     */
    public LuaValue getPreloadedScript(String name)
    {
        return preloadedScripts.getOrDefault(name, LuaValue.NIL);
    }

    /**
     * Executes a Lua function by name with optional arguments.
     *
     * @param functionName The name of the Lua function.
     * @param args         The arguments to pass to the function.
     * @return The result of the function call, or NIL if the function is not found.
     */
    public LuaValue callFunction(String functionName, LuaValue... args)
    {
        LuaValue function = globals.get(functionName);

        if (function.isnil())
        {
            LOGGER.warning("Lua function not found: " + functionName);
            return LuaValue.NIL;
        }

        try
        {
            return function.invoke(args).arg1();
        }
        catch (Exception e)
        {
            LOGGER.severe("Error calling Lua function: " + functionName + ": " + e.getMessage());
            return LuaValue.NIL;
        }
    }
}
