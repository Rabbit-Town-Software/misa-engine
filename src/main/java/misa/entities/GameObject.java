package misa.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * this class is made to be extended by specific game entities like players, enemies, NPCs, etc.
 * it has basic position and rendering properties
 */
@SuppressWarnings("unused")
public abstract class GameObject
{
    private static final Logger LOGGER = Logger.getLogger(GameObject.class.getName());

    protected double x, y; // coordinates
    protected BufferedImage currentImage;

    /**
     * constructor for GameObject
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * draws the GameObject on the screen
     *
     * @param graphics the graphics used for drawing
     */
    public void draw(Graphics graphics)
    {
        if (currentImage != null)
        {
            graphics.drawImage(currentImage, (int) x, (int) y, null);
        }
        else
        {
            LOGGER.warning("There is no image to draw for GameObject. ");
        }
    }

    /**
     * sets the current image to be displayed
     *
     * @param image image to be shown
     */
    public void setCurrentImage(BufferedImage image)
    {
        if (image == null)
        {
            LOGGER.warning("currentImage is null, GameObject might not render correctly. ");
        }
        this.currentImage = image;
    }

    /**
     * updates the GameObject's state
     * specific behaviors like movement and interactions should be defined in this method
     */
    public abstract void update();


    // getters and setters below
    /**
     * sets the new position of the GameObject
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return returns the x coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * @return returns the y coordinate
     */
    public double getY()
    {
        return y;
    }
}
