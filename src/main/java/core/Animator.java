package core;

import java.awt.image.BufferedImage;

public class Animator
{
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private long frameDuration = 200;

    public void animate(BufferedImage[] animationFrames,
                        boolean shouldLoop,
                        GameObject gameObject)
    {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime > frameDuration)
        {
            currentFrame++;
            lastFrameTime = currentTime;
        }
        if (currentFrame >= animationFrames.length)
        {
            currentFrame = shouldLoop ? 0 : animationFrames.length - 1;
        }
        BufferedImage currentFrameImage = animationFrames[currentFrame];
        gameObject.setCurrentImage(currentFrameImage);
    }
}
