package misa.core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * GameCanvas is the main drawing surface for the game.
 * <p>
 * It handles the connection between the Swing window and the Renderer,
 * drawing every frame by passing the Graphics2D context down to the Renderer.
 * <p>
 * This class is designed to be placed inside a JFrame or GameWindow.
 */
public class GameCanvas extends JPanel
{
    private final Renderer renderer; // Renderer responsible for all drawing operations

    /**
     * Creates a new GameCanvas instance.
     *
     * @param renderer The Renderer that will handle all game rendering.
     */
    public GameCanvas(Renderer renderer)
    {
        this.renderer = renderer;

        // Make sure the panel is ready to be drawn on
        setOpaque(true);
        setBackground(Color.BLACK);

        // Allow this panel to receive focus for input handling if needed
        setFocusable(true);
        requestFocus();
    }

    /**
     * Called automatically by Swing whenever the canvas needs to be redrawn.
     *
     * @param graphics The Graphics context provided by the Swing system.
     */
    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        if (graphics instanceof Graphics2D graphics2D)
        {
            // Pass the Graphics2D context to the Renderer for drawing
            renderer.render(graphics2D);
        }
    }
}
