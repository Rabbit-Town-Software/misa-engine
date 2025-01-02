package misa.core.events.rendering;

import misa.core.events.EventListener;

/**
 * Listens for the RenderEndEvent and performs actions when the rendering process ends.
 */
@SuppressWarnings("unused")
public class RenderEndListener implements EventListener<RenderEndEvent>
{
    @Override
    public void handleEvent(RenderEndEvent event)
    {
        // Actions to take when the rendering process ends
        System.out.println("Rendering Ended");

        // Example: Cleanup rendering resources, finalize drawing, etc.
        // finalizeRendering();
    }
}
