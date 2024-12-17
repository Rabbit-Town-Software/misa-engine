package misa.core.events.input;

import misa.core.events.EventListener;

/**
 * Listens for the KeyPressEvent and performs actions when a key is pressed.
 */
@SuppressWarnings("unused")
public class KeyPressListener implements EventListener<KeyPressEvent>
{
    @Override
    public void handleEvent(KeyPressEvent event)
    {
        // Perform actions when a key is pressed
        System.out.println("Key pressed: " + event.getKeyCode());

        // Example: If the key is 'W', make the player move up
        // if (event.getKeyCode() == KeyEvent.VK_W) { player.moveUp(); }
    }
}
