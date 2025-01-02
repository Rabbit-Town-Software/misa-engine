package misa.core.events.input;

import misa.core.events.EventListener;

/**
 * Listens for the MouseClickEvent and performs actions when a mouse button is clicked.
 */
@SuppressWarnings("unused")
public class MouseClickListener implements EventListener<MouseClickEvent>
{
    @Override
    public void handleEvent(MouseClickEvent event)
    {
        // Perform actions when the mouse is clicked
        System.out.println("Mouse clicked at (" + event.getX() + ", " + event.getY() + ") with button " + event.getButton());

        // Example: If the left mouse button is clicked, interact with the tile at the clicked position
        // if (event.getButton() == MouseEvent.BUTTON1) { interactWithTile(event.getX(), event.getY()); }
    }
}
