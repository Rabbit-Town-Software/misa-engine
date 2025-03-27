package misa.core;

import misa.data.tiled2misa.TiledLayer;
import misa.data.tiled2misa.TiledMap;
import misa.data.tiled2misa.TiledTileset;
import misa.entities.GameObject;
import misa.entities.Player;
import misa.systems.camera.Camera;
import misa.systems.camera.CameraManager;
import misa.systems.camera.CameraBoundary;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Renderer
{
    private Graphics2D graphics;                     // Graphics2D object for rendering
    private final CameraManager cameraManager;       // CameraManager to handle camera logic
    private final TiledMap tiledMap;                 // The map to be rendered (tilemap)
    private final ArrayList<GameObject> gameObjects; // List of GameObjects to render
    private boolean debugMode;                       // Flag to toggle the debug overlay

    // Constructor to initialize Renderer with CameraManager and TileMap
    public Renderer(Camera camera, CameraBoundary cameraBoundary, TiledMap tiledMap)
    {
        this.cameraManager = new CameraManager(camera, cameraBoundary); // Initialize CameraManager
        this.tiledMap = tiledMap;
        this.gameObjects = new ArrayList<>();
        this.debugMode = false;  // Default to false
    }

    // Add a sprite to the rendering list
    public void addGameObject(GameObject gameObject)
    {
        this.gameObjects.add(gameObject);
    }

    // Remove a sprite by its index in the list
    public void removeGameObject(GameObject gameObject)
    {
        this.gameObjects.remove(gameObject);
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
    public void render(Graphics2D graphics)
    {
        this.graphics = graphics;

        // Update camera (movement, zoom, and boundary enforcement)
        cameraManager.update(1.0f); // Assuming deltaTime = 1.0f for simplicity

        // Apply camera transformations (translate, scale, etc.)
        applyCameraTransformation();

        renderMapLayers();
        renderGameObjects();

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

    private void renderMapLayers()
    {
        if (tiledMap == null) return;

        for (TiledLayer layer : tiledMap.getLayers())
        {
            if (layer.name().equals("Background"))  // Rendering the Background layer as an example
            {
                renderLayer(layer);
            }
        }
    }

    private void renderLayer(TiledLayer layer)
    {
        int tileWidth = tiledMap.getTileWidth();
        int tileHeight = tiledMap.getTileHeight();

        for (int y = 0; y < layer.height(); y++)
        {
            for (int x = 0; x < layer.width(); x++)
            {
                long tileID = layer.tileData()[y][x];
                if (tileID == 0) continue; // Skip empty tiles

                TiledTileset tileset = getTilesetForTile(tileID);
                if (tileset != null)
                {
                    int localID = (int) (tileID - tileset.firstGID());
                    Image tileImage = tileset.getImage();

                    if (tileImage != null)
                    {
                        // Draw the tile at the correct position
                        graphics.drawImage(tileImage, x * tileWidth, y * tileHeight, tileWidth, tileHeight, null);
                    }
                }
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

    private void renderGameObjects()
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(this.graphics);
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
