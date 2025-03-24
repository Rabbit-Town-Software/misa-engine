package misa.core;

import javax.swing.*;

@SuppressWarnings("unused")
public class GameWindow
{
    public static void launchGame(String title, int width, int height,
                                  boolean fullscreen, boolean undecorated,
                                  GameLoop gameLoop)
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setUndecorated(undecorated);

            GameCanvas gameCanvas = gameLoop.getGameCanvas();
            frame.add(gameCanvas);

            if (fullscreen)
            {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setResizable(false);
            }
            else
            {
                frame.setSize(width, height);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
            }

            frame.setVisible(true);
            gameLoop.start();
        });
    }

    // Overload: Default title and resolution, windowed mode
    public static void launchGame(GameLoop gameLoop)
    {
        launchGame("Untitled Game", 800, 600, false, false, gameLoop);
    }

    // Overload: Custom size + fullscreen
    public static void launchGame(String title, int width, int height, boolean fullscreen, GameLoop gameLoop)
    {
        launchGame(title, width, height, fullscreen, false, gameLoop);
    }
}
