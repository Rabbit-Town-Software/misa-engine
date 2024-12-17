package misa.core.events.lifecycle;

import misa.core.events.EventListener;

/**
 * Listens for the GameStartEvent and performs actions when the game starts.
 */
@SuppressWarnings("unused")
public class GameStartListener implements EventListener<GameStartEvent>
{
    @Override
    public void handleEvent(GameStartEvent event)
    {
        // Perform actions when the game starts
        System.out.println("Game Started");

        // Example: Initialize game settings, set up the starting scene, etc.
        // initializeGameSettings();
    }
}
