package misa.core.events.input;

import misa.core.events.EventListener;

/**
 * Listens for the KeyReleaseEvent and performs actions when a key is released.
 */
@SuppressWarnings("unused")
public class KeyReleaseListener implements EventListener<KeyReleaseEvent>
{
    @Override
    public void handleEvent(KeyReleaseEvent event)
    {
        // Perform actions when a key is released
        System.out.println("Key released: " + event.getKeyCode());

        // Example: If the key is 'W', stop moving the player up
        // if (event.getKeyCode() == KeyEvent.VK_W) { player.stopMovingUp(); }
    }
}
