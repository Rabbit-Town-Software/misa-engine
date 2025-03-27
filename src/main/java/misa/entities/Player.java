package misa.entities;

import java.awt.image.BufferedImage;

/**
 * A simple player GameObject for testing purposes.
 */
public class Player extends GameObject
{
    public Player(double coordinateX,
                  double coordinateY,
                  boolean shouldAnimate,
                  boolean shouldLoop,
                  BufferedImage[] animationFrames)
    {
        super(coordinateX, coordinateY, shouldAnimate, shouldLoop, animationFrames);
    }
}
