package misa.core;

import misa.data.tiled2misa.TiledMap;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Renderer
{
    private Graphics2D graphics;       // Graphics2D object for rendering
    private Camera camera;             // Camera to handle the view transformation
    private TiledMap tileMap;          // The map to be rendered (tilemap)
    private ArrayList<Sprite> sprites; // List of sprites to render
    private boolean debugMode;         // Flag to toggle the debug overlay

    // Constructor to initialize Renderer with Camera and TileMap
    public Renderer(Camera camera, TiledMap tileMap)
    {
        this.camera = camera;
        this.tileMap = tileMap;
        this.sprites = new ArrayList<>();
        this.debugMode = false; // Default to false
    }

    // Add a sprite to the rendering list
    public void addSprite(Sprite sprite)
    {
        this.sprites.add(sprite);
    }

    // Remove a sprite by its index in the list
    public void removeSprite(int index)
    {
        if (index >= 0 && index < sprites.size()) {
            this.sprites.remove(index);
        }
    }

    // Toggle the debug mode (to show debug overlays like FPS, hitboxes, etc.)
    public void toggleDebugMode(boolean debugMode)
    {
        this.debugMode = debugMode;
    }

    // Main render function to handle the entire rendering process
    public void render(Graphics g)
    {
        this.graphics = (Graphics2D) g;

        // Apply camera transformation (translate, scale, etc.)
        applyCameraTransformation();

        // Render the tilemap
        renderTiles();

        // Render the sprites
        renderSprites();

        // Render the debug overlay if debugMode is enabled
        if (debugMode)
        {
            renderDebugOverlay();
        }
    }

    // Apply camera transformations (e.g., translating, zooming)
    private void applyCameraTransformation()
    {
        // Placeholder for camera transformation logic
        // For example: this.graphics.translate(camera.getX(), camera.getY());
    }

    // Render the tiles (currently a placeholder)
    private void renderTiles()
    {
        // Placeholder for tilemap rendering logic
        // Example: tileMap.render(this.graphics);
    }

    // Render the sprites (currently a placeholder)
    private void renderSprites()
    {
        // Placeholder for sprite rendering logic
        for (Sprite sprite : sprites)
        {
            // Example: sprite.render(this.graphics);
        }
    }

    // Render the debug overlay (e.g., FPS, hitboxes, etc.)
    private void renderDebugOverlay()
    {
        // Placeholder for debug overlay rendering logic
        // Example: this.graphics.drawString("FPS: " + fps, 10, 10);
    }
}
