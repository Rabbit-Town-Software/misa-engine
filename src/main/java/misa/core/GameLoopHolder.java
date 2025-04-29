package misa.core;

/**
 * GameLoopHolder provides static access to the active GameLoop instance.
 * <p>
 * This allows global systems (such as GameCanvas, input systems, or debugging tools)
 * to safely query information like deltaTime without needing a direct reference.
 */
@SuppressWarnings("unused")
public class GameLoopHolder
{
    // The currently active GameLoop instance
    private static GameLoop currentGameLoop;

    /**
     * Sets the current active GameLoop instance.
     *
     * @param gameLoop The GameLoop to store.
     */
    public static void set(GameLoop gameLoop)
    {
        currentGameLoop = gameLoop;
    }

    /**
     * Gets the current active GameLoop instance.
     *
     * @return The current GameLoop, or null if not set.
     */
    public static GameLoop get()
    {
        return currentGameLoop;
    }

    /**
     * Gets the current frame's deltaTime in seconds.
     * <p>
     * Useful for animations, movement updates, and other time-based calculations.
     *
     * @return deltaTime if GameLoop exists, otherwise 0.0f.
     */
    public static float getDeltaTime()
    {
        return currentGameLoop != null ? currentGameLoop.getDeltaTime() : 0f;
    }
}
