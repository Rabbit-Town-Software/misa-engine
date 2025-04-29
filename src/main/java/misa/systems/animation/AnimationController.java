package misa.systems.animation;

import misa.entities.GameObject;
import java.util.Map;
import java.awt.image.BufferedImage;

/**
 * AnimationController manages switching animations for a GameObject.
 *
 * <p>
 * It simplifies logic like:
 * - Setting the initial animations
 * - Playing a specific named animation
 * - Checking if a specific animation is currently playing
 * </p>
 */
public class AnimationController
{
    private final GameObject gameObject;
    private final Map<String, BufferedImage[]> animations;
    private String currentAnimationName = null;

    /**
     * Creates a new AnimationController for a GameObject.
     *
     * @param gameObject The GameObject to control.
     * @param animations Map of animation names to frame arrays.
     */
    public AnimationController(GameObject gameObject, Map<String, BufferedImage[]> animations)
    {
        this.gameObject = gameObject;
        this.animations = animations;

        // Assign the animation map to the GameObject
        gameObject.setNamedAnimations(animations);
    }

    /**
     * Plays a specific animation by name.
     * <p>
     * If the animation is already playing, it does nothing.
     *
     * @param name Name of the animation to play.
     */
    public void play(String name)
    {
        if (!animations.containsKey(name))
        {
            System.err.println("[AnimationController] Missing animation: " + name);
            return;
        }

        // Only change if different animation requested
        if (!name.equals(currentAnimationName))
        {
            gameObject.playAnimation(name);
            currentAnimationName = name;
        }
    }

    /**
     * Checks if a specific animation is currently playing.
     *
     * @param name Name of the animation to check.
     * @return True if the specified animation is currently playing.
     */
    public boolean isPlaying(String name)
    {
        return !name.equals(currentAnimationName);
    }
}
