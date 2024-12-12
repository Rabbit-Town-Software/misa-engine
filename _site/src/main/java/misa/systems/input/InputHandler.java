package misa.systems.input;

import misa.scripting.LuaManager;
import org.luaj.vm2.LuaValue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class to handle keyboard input and set up key bindings.
 * Supports Lua scripting for defining dynamic key actions.
 */
@SuppressWarnings("unused")
public class InputHandler
{
    private static final Logger LOGGER = Logger.getLogger(InputHandler.class.getName());

    private final LuaManager luaManager;

    /**
     * Constructor for InputHandler
     *
     * @param luaManager The LuaManager to handle scripting.
     */
    public InputHandler(LuaManager luaManager)
    {
        this.luaManager = luaManager;
    }

    /**
     * Sets up key bindings so that the provided actions will trigger on key press and release events.
     *
     * @param component The JComponent to attach key bindings to.
     * @param key       The key for which the actions will be set.
     * @param onPress   The action to execute when the key is pressed.
     * @param onRelease The action to execute when the key is released.
     */
    public void setupKeyBindings(JComponent component, String key,
                                 Runnable onPress, Runnable onRelease)
    {
        if (component == null || key == null || onPress == null || onRelease == null)
        {
            LOGGER.warning("Invalid input parameters... " +
                    "component, key, onPress, and onRelease cannot be null.");
            return;
        }

        // Setup key press action
        String pressActionKey = key + "Press";
        setupKeyAction(component, KeyStroke.getKeyStroke(key), pressActionKey, onPress, true);

        // Setup key release action
        String releaseActionKey = key + "Release";
        setupKeyAction(component, KeyStroke.getKeyStroke("released " + key), releaseActionKey, onRelease, false);
    }

    /**
     * Sets up key bindings using a Lua script.
     *
     * @param component The JComponent to attach key bindings to.
     * @param luaScript A Lua script defining the key bindings and actions.
     */
    public void setupKeyBindingsFromLua(JComponent component, String luaScript)
    {
        var scriptResult = luaManager.loadScript(luaScript);

        if (!scriptResult.istable())
        {
            LOGGER.warning("Lua script must return a table with key bindings.");
            return;
        }

        // Iterate over each key-action pair in the Lua table
        var keys = scriptResult.checktable().keys();
        for (var key : keys)
        {
            var keyConfig = scriptResult.get(key);

            if (!keyConfig.istable())
            {
                LOGGER.warning("Each key binding must be a table with 'onPress' and 'onRelease' functions.");
                continue;
            }

            String keyName = key.tojstring();
            LuaValue onPress = keyConfig.get("onPress");
            LuaValue onRelease = keyConfig.get("onRelease");

            setupKeyBindings(
                    component,
                    keyName,
                    () -> executeLuaAction(onPress, "onPress", keyName),
                    () -> executeLuaAction(onRelease, "onRelease", keyName)
            );
        }
    }

    /**
     * Executes a Lua action.
     *
     * @param luaAction The Lua function to execute.
     * @param actionType The type of action (e.g., "onPress" or "onRelease").
     * @param keyName The key associated with the action.
     */
    private void executeLuaAction(LuaValue luaAction, String actionType, String keyName)
    {
        if (luaAction == null || !luaAction.isfunction())
        {
            LOGGER.warning("Invalid Lua action for " + actionType + " on key: " + keyName);
            return;
        }

        try
        {
            luaAction.call();
            LOGGER.info("Executed Lua action: " + actionType + " for key: " + keyName);
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error executing Lua action for " + actionType + " on key: " + keyName, e);
        }
    }

    /**
     * Helper to set up a key action.
     *
     * @param component The JComponent to attach the action to.
     * @param keyStroke The keystroke that triggers the action.
     * @param actionKey The identifier for the action.
     * @param action    The runnable action that executes when triggered.
     * @param isPressed True if the action is key press, false if the action is key release.
     */
    private void setupKeyAction(JComponent component, KeyStroke keyStroke,
                                String actionKey, Runnable action, boolean isPressed)
    {
        if (keyStroke == null)
        {
            LOGGER.warning("Invalid KeyStroke for action: " + actionKey);
            return;
        }

        // Bind the key action to the component
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionKey);
        component.getActionMap().put(actionKey, new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                action.run();
            }
        });

        String eventType = isPressed ? "Press" : "Release";
        LOGGER.log(Level.INFO, "Key binding is set up: {0} ({1})",
                new Object[]{actionKey, eventType});
    }
}
