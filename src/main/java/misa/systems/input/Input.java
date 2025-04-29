package misa.systems.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Input system for tracking key states globally.
 *
 * <p>
 * Keeps a set of currently pressed keys using AWT KeyEvents.
 * Intended to be attached to a GameCanvas or JFrame.
 * </p>
 */
@SuppressWarnings("unused")
public class Input implements KeyListener
{
    // Set of currently pressed key codes
    private static final Set<Integer> pressedKeys = new HashSet<>();

    /**
     * Called when a key is pressed.
     *
     * @param keyEvent KeyEvent containing key information.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        pressedKeys.add(keyEvent.getKeyCode());
    }

    /**
     * Called when a key is released.
     *
     * @param keyEvent KeyEvent containing key information.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        pressedKeys.remove(keyEvent.getKeyCode());
    }

    /**
     * Called when a key is typed.
     * <p>
     * (Not used in this system.)
     *
     * @param keyEvent KeyEvent containing key information.
     */
    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
        // Typing events are ignored
    }

    /**
     * Checks whether a specific key is currently held down.
     *
     * @param keyCode KeyEvent.VK_* code to check.
     * @return True if the key is pressed, false otherwise.
     */
    public static boolean isKeyPressed(int keyCode)
    {
        return pressedKeys.contains(keyCode);
    }
}
