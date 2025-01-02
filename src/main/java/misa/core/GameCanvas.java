package misa.core;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameCanvas extends Canvas
{

    private final Renderer renderer;

    public GameCanvas(Renderer renderer)
    {
        this.renderer = renderer;
    }

    @Override
    public void paint(Graphics graphics)
    {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        renderer.render(graphics2D);
    }
}
