package misa.core;

import misa.data.tiled2misa.TiledLayer;
import misa.data.tiled2misa.TiledMap;
import misa.data.tiled2misa.TiledObject;
import misa.data.tiled2misa.TiledTileset;
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
    private final TiledMap tiledMap;            // The map to be rendered (tilemap)
    private ArrayList<Sprite> sprites;         // List of sprites to render
    private boolean debugMode;                 // Flag to toggle the debug overlay

    // Constructor to initialize Renderer with CameraManager and TileMap
    public Renderer(Camera camera, CameraBoundary cameraBoundary, TiledMap tiledMap)
    {
        this.cameraManager = new CameraManager(camera, cameraBoundary); // Initialize CameraManager
        this.tiledMap = tiledMap;
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
    public void render(Graphics2D g)
    {
        this.graphics = g;

        // Update camera (movement, zoom, and boundary enforcement)
        cameraManager.update(1.0f); // Assuming deltaTime = 1.0f for simplicity

        // Apply camera transformations (translate, scale, etc.)
        applyCameraTransformation();

        // Render the tilemap
        for (TiledLayer layer : tiledMap.getLayers())
        {
            renderLayer(g, layer);
        }

        renderObjects(g);

        // Render the sprites
        renderSprites();

        // Render the debug overlay if debugMode is enabled
        if (debugMode)
        {
            renderDebugOverlay();
        }
    }

    private void renderLayer(Graphics2D g, TiledLayer layer)
    {
        int tileWidth = tiledMap.getTileWidth();
        int tileHeight = tiledMap.getTileHeight();
        TiledTileset currentTileset = null;

        for (int y = 0; y < layer.height(); y++)
        {
            for (int x = 0; x < layer.width(); x++)
            {
                long tileID = layer.tileData()[y][x];
                TiledTileset tileset = getTilesetForTile(tileID);

                if (tileset != currentTileset) currentTileset = tileset;

                g.drawImage(currentTileset.getImage(), x * tileWidth, y * tileHeight,
                        tileWidth, tileHeight, null);
            }
        }
    }

    private TiledTileset getTilesetForTile(long tileID)
    {
        for (TiledTileset tileset : tiledMap.getTilesets())
        {
            if (tileID >= tileset.firstGID()) return tileset;
        }
        return null;
    }

    private void renderObjects(Graphics2D g)
    {
        for (TiledObject object : tiledMap.getObjects())
        {
            g.fillRect((int) object.x(), (int) object.y(),
                    (int) object.width(), (int) object.height());
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
