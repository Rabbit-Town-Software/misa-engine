package misa.core;

/**
 * Represents something that can be started once before the game loop begins running.
 * <p>
 * This is typically used for initializing GameObjects, systems, or loading resources.
 */
public interface Startable
{
    /**
     * Called once before the main game loop starts.
     * <p>
     * Used for setup logic such as loading textures, setting initial state, etc.
     */
    void start();
}
