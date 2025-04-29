package misa.core;

import javax.swing.*;

/**
 * GameWindow creates a basic game window with a GameCanvas attached.
 * <p>
 * This class simply creates and returns a configured JFrame.
 * It does not automatically start the GameLoop — the user must call start() manually.
 */
@SuppressWarnings("unused")
public class GameWindow
{
    /**
     * Creates and returns a new game window.
     *
     * @param title        Title of the window.
     * @param width        Width of the window.
     * @param height       Height of the window.
     * @param fullscreen   Whether to launch in fullscreen mode.
     * @param undecorated  Whether to remove window borders and decorations.
     * @param gameLoop     The GameLoop that provides the GameCanvas to attach.
     * @return The configured JFrame.
     */
    public static JFrame create(
            String title,
            int width,
            int height,
            boolean fullscreen,
            boolean undecorated,
            GameLoop gameLoop
    )
    {
        // Create the main window (JFrame) with the provided title
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set whether the window should have borders, title bar, etc.
        frame.setUndecorated(undecorated);

        // Attach the game's rendering surface (GameCanvas) to the window
        GameCanvas gameCanvas = gameLoop.getGameCanvas();
        frame.add(gameCanvas);

        // If fullscreen, maximize the window to fill the screen
        if (fullscreen)
        {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        else
        {
            // Otherwise, set window size manually and center it
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
        }

        // Prevent resizing — game canvas assumes fixed dimensions
        frame.setResizable(false);

        // Return the fully configured frame (user must call setVisible and start GameLoop manually)
        return frame;
    }
}
