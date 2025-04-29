package misa.core;

/**
 * Represents an object that can be updated every frame or tick.
 * <p>
 * Typically used for things like player movement, AI behaviors, animations, or
 * systems that need to update in sync with the game loop.
 */
public interface Updatable
{
    /**
     * Called once per update cycle (tick) by the GameLoop.
     * <p>
     * This method should contain logic that needs to run every frame.
     */
    void update();
}
