package misa.core.events.lifecycle;

import misa.core.events.EventListener;

/**
 * Listens for the GameResumeEvent and performs actions when the game is resumed.
 */
@SuppressWarnings("unused")
public class GameResumeListener implements EventListener<GameResumeEvent>
{
    @Override
    public void handleEvent(GameResumeEvent event)
    {
        // Perform actions when the game is resumed
        System.out.println("Game Resumed");

        // Example: Hide pause menu, resume the game clock, etc.
        // hidePauseMenu();
    }
}
