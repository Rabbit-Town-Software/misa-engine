package misa.core.events.lifecycle;

import misa.core.events.EventListener;

/**
 * Listens for the GamePauseEvent and performs actions when the game is paused.
 */
@SuppressWarnings("unused")
public class GamePauseListener implements EventListener<GamePauseEvent>
{
    @Override
    public void handleEvent(GamePauseEvent event)
    {
        // Perform actions when the game is paused
        System.out.println("Game Paused");

        // Example: Show a pause menu, stop the game clock, etc.
        // displayPauseMenu();
    }
}
