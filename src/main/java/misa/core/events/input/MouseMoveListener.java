package misa.core.events.input;

import misa.core.events.EventListener;

/**
 * Listens for the MouseMoveEvent and performs actions when the mouse moves.
 */
@SuppressWarnings("unused")
public class MouseMoveListener implements EventListener<MouseMoveEvent>
{
    @Override
    public void handleEvent(MouseMoveEvent event)
    {
        // Perform actions when the mouse moves
        System.out.println("Mouse moved to (" + event.getX() + ", " + event.getY() + ")");

        // Example: If the mouse moves, update the UI to show a preview of the tile under the mouse
        // updateTilePreview(event.getX(), event.getY());
    }
}
