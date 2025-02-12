package misa.core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class GameCanvas extends JPanel
{
    private final Renderer renderer;

    public GameCanvas(Renderer renderer)
    {
        this.renderer = renderer;
        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // Fill the background with black to avoid a white screen
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        // Render game objects
        renderer.render(graphics2D);
    }
}
