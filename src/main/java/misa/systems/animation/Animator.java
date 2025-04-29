package misa.systems.animation;

import misa.entities.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * Animator handles frame-based animation for GameObjects.
 *
 * <p>
 * Provides two main rendering modes:
 * - Unit-space drawing (scaled 1x1 unit)
 * - Pixel-space drawing (for manual pixel positioning)
 * </p>
 */
@SuppressWarnings("unused")
public class Animator
{
    private static final Logger LOGGER = Logger.getLogger(Animator.class.getName());

    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private long frameDuration = 200; // Frame duration in milliseconds

    /**
     * Animates a GameObject by rendering its current frame with camera offset.
     *
     * @param animationFrames Array of animation frames.
     * @param shouldLoop Whether animation should loop.
     * @param gameObject The GameObject being animated.
     * @param graphics2D The Graphics2D context.
     * @param offsetX World/camera X offset.
     * @param offsetY World/camera Y offset.
     */
    public void animate(
            BufferedImage[] animationFrames,
            boolean shouldLoop,
            GameObject gameObject,
            Graphics2D graphics2D,
            float offsetX,
            float offsetY
    )
    {
        if (animationFrames == null || animationFrames.length == 0)
        {
            LOGGER.warning("Animation frames are empty or null. Animation skipped.");
            return;
        }

        long currentTime = System.nanoTime();
        if (shouldUpdateFrame(currentTime))
        {
            updateCurrentFrame(animationFrames, shouldLoop);
            lastFrameTime = currentTime;
        }

        renderCurrentFrame(animationFrames, gameObject, graphics2D, offsetX, offsetY);
    }

    /**
     * Determines if it's time to advance to the next frame.
     *
     * @param currentTime Current time in nanoseconds.
     * @return True if enough time has passed since last frame.
     */
    private boolean shouldUpdateFrame(long currentTime)
    {
        long elapsedTime = (currentTime - lastFrameTime) / 1_000_000; // Convert to milliseconds
        return elapsedTime > frameDuration;
    }

    /**
     * Advances to the next frame, respecting looping settings.
     *
     * @param animationFrames Array of animation frames.
     * @param shouldLoop Whether to loop or stop at last frame.
     */
    private void updateCurrentFrame(BufferedImage[] animationFrames, boolean shouldLoop)
    {
        currentFrame++;
        if (currentFrame >= animationFrames.length)
        {
            if (shouldLoop)
            {
                currentFrame = 0;
            }
            else
            {
                currentFrame = animationFrames.length - 1;
            }
        }
    }

    /**
     * Renders the current frame of the animation at unit-space position.
     *
     * @param animationFrames Array of animation frames.
     * @param gameObject The GameObject.
     * @param graphics2D The Graphics2D context.
     * @param offsetX X offset for world or camera.
     * @param offsetY Y offset for world or camera.
     */
    private void renderCurrentFrame(
            BufferedImage[] animationFrames,
            GameObject gameObject,
            Graphics2D graphics2D,
            float offsetX,
            float offsetY
    )
    {
        BufferedImage currentFrameImage = animationFrames[currentFrame];

        graphics2D.drawImage(
                currentFrameImage,
                (int)(gameObject.getCoordinateX() + offsetX),
                (int)(gameObject.getCoordinateY() + offsetY),
                (int)(gameObject.getCoordinateX() + offsetX + 1),
                (int)(gameObject.getCoordinateY() + offsetY + 1),
                0, 0,
                currentFrameImage.getWidth(),
                currentFrameImage.getHeight(),
                null
        );
    }

    /**
     * Animates the GameObject directly at a pixel position.
     *
     * @param animationFrames Array of animation frames.
     * @param shouldLoop Whether animation should loop.
     * @param gameObject The GameObject being animated.
     * @param graphics The Graphics2D context.
     * @param pixelX Pixel X position to draw at.
     * @param pixelY Pixel Y position to draw at.
     * @param sizeInPixels Width/height of the sprite to draw.
     */
    public void animateAtPixel(
            BufferedImage[] animationFrames,
            boolean shouldLoop,
            GameObject gameObject,
            Graphics2D graphics,
            int pixelX,
            int pixelY,
            int sizeInPixels
    )
    {
        if (animationFrames == null || animationFrames.length == 0)
        {
            LOGGER.warning("Animation frames are empty or null. Animation skipped.");
            return;
        }

        long currentTime = System.nanoTime();
        if (shouldUpdateFrame(currentTime))
        {
            updateCurrentFrame(animationFrames, shouldLoop);
            lastFrameTime = currentTime;
        }

        BufferedImage currentFrameImage = animationFrames[currentFrame];

        graphics.drawImage(
                currentFrameImage,
                pixelX, pixelY,
                pixelX + sizeInPixels,
                pixelY + sizeInPixels,
                0, 0,
                currentFrameImage.getWidth(),
                currentFrameImage.getHeight(),
                null
        );
    }

    /**
     * Sets the frame duration (speed of animation).
     *
     * @param duration Frame duration in milliseconds.
     */
    public void setFrameDuration(long duration)
    {
        if (duration <= 0)
        {
            LOGGER.warning("Invalid frame duration. Resetting to default 200ms.");
            this.frameDuration = 200;
        }
        else
        {
            this.frameDuration = duration;
            LOGGER.info("Frame duration set to: " + duration + "ms");
        }
    }
}
