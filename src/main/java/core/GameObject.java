package core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
    protected double x, y;
    protected BufferedImage currentImage;

    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g)
    {
        if (currentImage != null)
        {
            g.drawImage(currentImage, (int) x, (int) y, null);
        }
    }

    public void setCurrentImage(BufferedImage image)
    {
        this.currentImage = image;
    }

    public abstract void update();
}
