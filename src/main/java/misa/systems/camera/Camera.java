package misa.systems.camera;

import java.awt.Graphics2D;

/**
 * Camera handles world-to-screen transformations for rendering.
 *
 * <p>
 * It defines a position (x, y) in world space and a viewport size in units.
 * The camera can move or jump instantly to a position.
 * </p>
 */
public class Camera
{
    private float x;
    private float y;

    private final float viewportUnitsWidth;
    private final float viewportUnitsHeight;

    /**
     * Creates a new Camera.
     *
     * @param startX Starting X position in world units.
     * @param startY Starting Y position in world units.
     * @param viewportUnitsWidth Width of the camera view in world units.
     * @param viewportUnitsHeight Height of the camera view in world units.
     */
    public Camera(float startX, float startY, float viewportUnitsWidth, float viewportUnitsHeight)
    {
        this.x = startX;
        this.y = startY;
        this.viewportUnitsWidth = viewportUnitsWidth;
        this.viewportUnitsHeight = viewportUnitsHeight;
    }

    /**
     * Moves the camera by a delta.
     *
     * @param dx Amount to move on the X axis (world units).
     * @param dy Amount to move on the Y axis (world units).
     */
    public void move(float dx, float dy)
    {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Sets the camera to a specific position instantly.
     *
     * @param x New X coordinate (world units).
     * @param y New Y coordinate (world units).
     */
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Current X position of the camera (world units).
     */
    public float getX()
    {
        return x;
    }

    /**
     * @return Current Y position of the camera (world units).
     */
    public float getY()
    {
        return y;
    }

    /**
     * @return Width of the viewport in world units.
     */
    public float getViewportUnitsWidth()
    {
        return viewportUnitsWidth;
    }

    /**
     * @return Height of the viewport in world units.
     */
    public float getViewportUnitsHeight()
    {
        return viewportUnitsHeight;
    }

    /**
     * Applies translation and scaling transforms to a Graphics2D context.
     *
     * @param graphics2D Graphics2D context to modify.
     * @param pixelsPerUnit How many pixels correspond to one world unit.
     */
    @SuppressWarnings("unused")
    public void applyTransformations(Graphics2D graphics2D, int pixelsPerUnit)
    {
        // First, translate based on camera position
        graphics2D.translate(-x * pixelsPerUnit, -y * pixelsPerUnit);

        // Then, scale the world space to screen pixels
        graphics2D.scale(pixelsPerUnit, pixelsPerUnit);
    }
}
