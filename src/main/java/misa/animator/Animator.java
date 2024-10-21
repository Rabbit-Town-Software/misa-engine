package misa.animator;

import misa.entities.GameObject;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

// this class takes the BufferedImage arrays and plays them in sequence
@SuppressWarnings("unused")
public class Animator
{
    private static final Logger LOGGER = Logger.getLogger(Animator.class.getName());

    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private long frameDuration = 200; // default frame duration in milliseconds

    /**
     * animate the given GameObject using the given animation frames
     *
     * @param animationFrames an array of images with the animation frames
     * @param shouldLoop      whether the animation should loop or not
     * @param gameObject      the GameObject the animation should be applied to
     */
    public void animate(BufferedImage[] animationFrames,
                        boolean shouldLoop, GameObject gameObject)
    {
        if (animationFrames == null || animationFrames.length == 0)
        {
            LOGGER.warning("There are no animation frames. ");
            return;
        }

        long currentTime = System.nanoTime();
        if (shouldUpdateFrame(currentTime))
        {
            updateCurrentFrame(animationFrames, shouldLoop);
            lastFrameTime = currentTime;
        }

        applyCurrentFrameToGameObject(animationFrames, gameObject);
    }


     // check if it's time to update to the next frame based on the frame duration
    private boolean shouldUpdateFrame(long currentTime)
    {
        long elapsedTime = (currentTime - lastFrameTime) / 1_000_000; // convert to milliseconds
        return elapsedTime > frameDuration;
    }

     // restart animation if it should loop
    private void updateCurrentFrame(BufferedImage[] animationFrames, boolean shouldLoop)
    {
        currentFrame++;
        if (currentFrame >= animationFrames.length)
        {
            if (shouldLoop)
            {
                LOGGER.info("Animation looped successfully. ");
                currentFrame = 0;
            }
            else
            {
                LOGGER.info("Animation finished successfully. ");
                currentFrame = animationFrames.length - 1;
            }
        }
    }


     // apply the current frame to the GameObject
    private void applyCurrentFrameToGameObject(BufferedImage[] animationFrames, GameObject gameObject)
    {
        BufferedImage currentFrameImage = animationFrames[currentFrame];
        gameObject.setCurrentImage(currentFrameImage);
    }

    /**
     * @param duration how long each frame should be shown
     */
    public void setFrameDuration(long duration)
    {
        if (duration <= 0)
        {
            LOGGER.warning("Frame duration must be positive. Reset to default value (200ms).");
            this.frameDuration = 200;
        }
        else
        {
            this.frameDuration = duration;
            LOGGER.info("Frame duration set to: " + duration + "ms");
        }
    }
}
