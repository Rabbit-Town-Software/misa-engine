package misa.core.events.lifecycle;

import misa.core.events.EventListener;

/**
 * Listens for the GameOverEvent and performs actions when the game ends.
 */
@SuppressWarnings("unused")
public class GameOverListener implements EventListener<GameOverEvent>
{
    @Override
    public void handleEvent(GameOverEvent event)
    {
        // Perform actions when the game is over
        System.out.println("Game Over: " + event.reason());

        // Example: Show a game over screen, reset stats, etc.
        // displayGameOverScreen(event.getReason());
    }
}
