package misa.core.events.rendering;

import misa.core.events.EventListener;

/**
 * Listens for the RenderStartEvent and performs actions when the rendering process starts.
 */
@SuppressWarnings("unused")
public class RenderStartListener implements EventListener<RenderStartEvent>
{
    @Override
    public void handleEvent(RenderStartEvent event)
    {
        // Actions to take when the rendering process starts
        System.out.println("Rendering Started");

        // Example: Set up rendering parameters, prepare resources, etc.
        // prepareRenderingResources();
    }
}
