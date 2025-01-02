package misa.core;

import javax.swing.JFrame;

@SuppressWarnings("unused")
public class GameWindow
{

    private final GameLoop gameLoop;

    public GameWindow(GameLoop gameLoop)
    {
        this.gameLoop = gameLoop;
        initialize();
    }

    private void initialize()
    {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Directly add the game canvas to the frame
        GameCanvas canvas = gameLoop.getGameCanvas();
        frame.add(canvas);

        // Set window size and make it visible
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public void start()
    {
        gameLoop.start();
    }
}
