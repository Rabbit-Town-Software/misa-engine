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
    private Graphics2D graphics;
    private final CameraManager cameraManager;
    private TiledMap tiledMap;
    private final ArrayList<GameObject> gameObjects;
    private boolean debugMode;

    public Renderer(Camera camera, CameraBoundary cameraBoundary, TiledMap tiledMap)
    {
        this.cameraManager = new CameraManager(camera, cameraBoundary);
        this.tiledMap = tiledMap;
        this.gameObjects = new ArrayList<>();
        this.debugMode = false;
    }

    public void addGameObject(GameObject gameObject)
    {
        this.gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject)
    {
        this.gameObjects.remove(gameObject);
    }

    public void toggleDebugMode(boolean debugMode)
    {
        this.debugMode = debugMode;
    }

    public void handleInput(float moveSpeed, float zoomSpeed, boolean moveLeft, boolean moveRight, boolean moveUp, boolean moveDown, boolean zoomIn, boolean zoomOut)
    {
        cameraManager.handleInput(
                moveSpeed, zoomSpeed,
                moveLeft, moveRight,
                moveUp, moveDown,
                zoomIn, zoomOut);
    }

    public void render(Graphics2D graphics)
    {
        this.graphics = graphics;

        cameraManager.update(1.0f); // Assuming deltaTime = 1.0f
        applyCameraTransformation();

        renderMapLayers();
        renderGameObjects();

        if (debugMode)
        {
            renderDebugOverlay();
        }
    }

    private void applyCameraTransformation()
    {
        graphics.translate(-cameraManager.getCamera().getX(),
                -cameraManager.getCamera().getY());
        graphics.scale(cameraManager.getCamera().getZoom(),
                cameraManager.getCamera().getZoom());
    }

    private void renderMapLayers()
    {
        if (tiledMap == null) return;

        for (TiledLayer layer : tiledMap.getLayers())
        {
            renderLayer(layer); // Don't filter by name
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
                if (tileID == 0) continue;

                TiledTileset tileset = getTilesetForTile(tileID);
                if (tileset != null)
                {
                    int localID = (int) (tileID - tileset.firstGID());
                    Image tilesetImage = tileset.getImage();

                    if (tilesetImage != null)
                    {
                        int tilesetWidth = tilesetImage.getWidth(null);
                        int tilesPerRow = tilesetWidth / tileWidth;

                        int srcX = (localID % tilesPerRow) * tileWidth;
                        int srcY = (localID / tilesPerRow) * tileHeight;

                        graphics.drawImage(
                                tilesetImage,
                                x * tileWidth, y * tileHeight,
                                x * tileWidth + tileWidth, y * tileHeight + tileHeight,
                                srcX, srcY,
                                srcX + tileWidth, srcY + tileHeight,
                                null
                        );
                    }
                    else
                    {
                        graphics.setColor(Color.RED);
                        graphics.fillRect(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
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

    private void renderDebugOverlay()
    {
        this.graphics.setColor(Color.WHITE);
        this.graphics.drawString("FPS: " + 60, 10, 10);
    }

    public void setTiledMap(TiledMap tiledMap)
    {
        this.tiledMap = tiledMap;
        System.out.println("TiledMap changed to: " + tiledMap);
    }
}
