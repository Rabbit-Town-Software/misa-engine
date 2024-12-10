package misa.core;

import misa.data.tiled2misa.TiledMap;
import misa.systems.camera.Camera;
import misa.systems.camera.CameraManager;
import misa.systems.camera.CameraBoundary;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Renderer
{
    private Graphics2D graphics;               // Graphics2D object for rendering
    private final CameraManager cameraManager; // CameraManager to handle camera logic
    private final TiledMap tileMap;            // The map to be rendered (tilemap)
    private ArrayList<Sprite> sprites;         // List of sprites to render
    private boolean debugMode;                 // Flag to toggle the debug overlay

    // Constructor to initialize Renderer with CameraManager and TileMap
    public Renderer(Camera camera, CameraBoundary cameraBoundary, TiledMap tileMap)
    {
        this.cameraManager = new CameraManager(camera, cameraBoundary); // Initialize CameraManager
        this.tileMap = tileMap;
        this.sprites = new ArrayList<>();
        this.debugMode = false;  // Default to false
    }

    // Add a sprite to the rendering list
    public void addSprite(Sprite sprite)
    {
        this.sprites.add(sprite);
    }

    // Remove a sprite by its index in the list
    public void removeSprite(int index)
    {
        if (index >= 0 && index < sprites.size())
        {
            this.sprites.remove(index);
        }
    }

    // Toggle the debug mode (to show debug overlays like FPS, hitboxes, etc.)
    public void toggleDebugMode(boolean debugMode)
    {
        this.debugMode = debugMode;
    }

    // Handle camera input (e.g., for movement, zooming)
    public void handleInput(float moveSpeed, float zoomSpeed, boolean moveLeft, boolean moveRight, boolean moveUp, boolean moveDown, boolean zoomIn, boolean zoomOut)
    {
        cameraManager.handleInput(
                moveSpeed, zoomSpeed,
                moveLeft, moveRight,
                moveUp, moveDown,
                zoomIn, zoomOut);
    }

    // Main render function to handle the entire rendering process
    public void render(Graphics g)
    {
        this.graphics = (Graphics2D) g;

        // Update camera (movement, zoom, and boundary enforcement)
        cameraManager.update(1.0f); // Assuming deltaTime = 1.0f for simplicity

        // Apply camera transformations (translate, scale, etc.)
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
        // Apply camera translation (move the world based on camera position)
        this.graphics.translate(-cameraManager.getCamera().getX(),
                -cameraManager.getCamera().getY());  // Offset the drawing by the camera's position

        // Apply camera zoom (scale the world based on the camera zoom level)
        this.graphics.scale(cameraManager.getCamera().getZoom(),
                cameraManager.getCamera().getZoom());  // Zoom in/out the world
    }

    // Render the tiles (currently a placeholder)
    private void renderTiles()
    {
        // Placeholder for tilemap rendering logic
        tileMap.render(this.graphics);  // Assuming TiledMap has a render method that takes Graphics2D
    }

    // Render the sprites (currently a placeholder)
    private void renderSprites()
    {
        // Placeholder for sprite rendering logic
        for (Sprite sprite : sprites)
        {
            sprite.render(this.graphics);  // Assuming Sprite has a render method that takes Graphics2D
        }
    }

    // Render the debug overlay (e.g., FPS, hitboxes, etc.)
    private void renderDebugOverlay()
    {
        // Example: Draw FPS or other debug info at a fixed position
        this.graphics.setColor(Color.WHITE);
        this.graphics.drawString("FPS: " + 60, 10, 10);  // Placeholder for FPS
    }
}
