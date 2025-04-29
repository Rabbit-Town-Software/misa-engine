package misa.core;

import misa.data.tiled2misa.TiledLayer;
import misa.data.tiled2misa.TiledMap;
import misa.data.tiled2misa.TiledTileset;
import misa.entities.GameObject;
import misa.systems.camera.Camera;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Renderer is responsible for drawing the game world onto the screen.
 * <p>
 * It handles rendering tilemaps, GameObjects, and managing camera offsets
 * to simulate movement through a larger world space.
 */
public class Renderer
{
    // Camera controls the view into the world
    private final Camera camera;

    // Currently loaded TiledMap, if any
    private TiledMap tiledMap;

    // List of all game objects that should be drawn
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    // How many pixels represent one unit in world space
    private final int pixelsPerUnit;

    /**
     * Creates a new Renderer.
     *
     * @param camera         Camera controlling the view offset.
     * @param tiledMap       Initial map to render (can be null).
     * @param pixelsPerUnit  Number of pixels that represent one world unit (must be > 0).
     */
    public Renderer(Camera camera, TiledMap tiledMap, int pixelsPerUnit)
    {
        if (pixelsPerUnit <= 0)
            throw new IllegalArgumentException("pixelsPerUnit must be > 0.");

        this.camera = camera;
        this.tiledMap = tiledMap;
        this.pixelsPerUnit = pixelsPerUnit;
    }

    /**
     * Adds a GameObject to be rendered every frame.
     *
     * @param gameObject The object to add.
     */
    public void addGameObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    /**
     * Sets the current TiledMap to render.
     *
     * @param tiledMap The new TiledMap.
     */
    public void setTiledMap(TiledMap tiledMap)
    {
        this.tiledMap = tiledMap;
    }

    /**
     * Renders the map and all game objects.
     *
     * @param graphics2D The Graphics2D context to draw onto.
     */
    public void render(Graphics2D graphics2D)
    {
        // Reset any previous transforms so we draw in clean pixel space
        graphics2D.setTransform(new AffineTransform());

        // Draw the tile map layers if a map is loaded
        if (tiledMap != null)
            renderMapLayers(graphics2D);

        // Draw all game objects
        renderGameObjects(graphics2D);
    }

    /**
     * Renders all layers of the currently loaded TiledMap.
     *
     * @param graphics2D The Graphics2D context to draw onto.
     */
    private void renderMapLayers(Graphics2D graphics2D)
    {
        for (TiledLayer tiledLayer : tiledMap.getLayers())
        {
            for (int y = 0; y < tiledLayer.height(); y++)
            {
                for (int x = 0; x < tiledLayer.width(); x++)
                {
                    long gid = tiledLayer.tileData()[y][x];
                    if (gid == 0) continue; // No tile here

                    TiledTileset ts = getTilesetForTile(gid);
                    if (ts == null) continue;

                    int localId = (int)(gid - ts.firstGID());
                    Image img = ts.getImage();
                    if (img == null) continue;

                    int sheetW = img.getWidth(null);
                    int tilesPerRow = sheetW / tiledMap.getTileWidth();

                    int sx = (localId % tilesPerRow) * tiledMap.getTileWidth();
                    int sy = (localId / tilesPerRow) * tiledMap.getTileHeight();

                    // Calculate screen pixel position (applying camera offset)
                    int pixelX = Math.round((x - camera.getX()) * pixelsPerUnit);
                    int pixelY = Math.round((y - camera.getY()) * pixelsPerUnit);

                    // Draw one tile from the tileset
                    graphics2D.drawImage(
                            img,
                            pixelX, pixelY,
                            pixelX + pixelsPerUnit, pixelY + pixelsPerUnit,
                            sx, sy,
                            sx + tiledMap.getTileWidth(), sy + tiledMap.getTileHeight(),
                            null
                    );
                }
            }
        }
    }

    /**
     * Finds the correct tileset for a given global tile ID (gid).
     *
     * @param gid The global tile ID.
     * @return The TiledTileset that contains this tile, or null if not found.
     */
    private TiledTileset getTilesetForTile(long gid)
    {
        TiledTileset best = null;

        // Search through all tilesets, find the highest firstGID that is <= gid
        for (TiledTileset ts : tiledMap.getTilesets())
        {
            if (gid >= ts.firstGID()) best = ts;
        }

        return best;
    }

    /**
     * Renders all GameObjects relative to the camera position.
     *
     * @param graphics2D The Graphics2D context to draw onto.
     */
    private void renderGameObjects(Graphics2D graphics2D)
    {
        for (GameObject obj : gameObjects)
        {
            // Convert world position into screen pixel coordinates
            int px = Math.round((float)(obj.getCoordinateX() - camera.getX()) * pixelsPerUnit);
            int py = Math.round((float)(obj.getCoordinateY() - camera.getY()) * pixelsPerUnit);

            // Let the GameObject draw itself
            obj.drawAtPixel(graphics2D, px, py, pixelsPerUnit);
        }
    }
}
