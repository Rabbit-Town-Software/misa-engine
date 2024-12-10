package misa.systems.camera;

import java.awt.*;

/**
 * The Camera class is responsible for managing the camera's position, zoom level,
 * and ensuring smooth transitions and boundary enforcement while rendering the world.
 * It supports features like:
 * - Smooth movement and zooming.
 * - Boundary enforcement to restrict camera movement.
 */
@SuppressWarnings("unused")
public class Camera
{
    // Current position of the camera in world coordinates
    private float x;
    private float y;

    // Current zoom level of the camera
    private float zoom;

    // Target position and zoom that the camera is transitioning towards
    private float targetX, targetY, targetZoom;

    // Factor that determines the smoothness of the camera's movement and zooming
    private final float smoothFactor;

    // Boundaries for camera movement (min and max X and Y coordinates)
    private final float minX, minY, maxX, maxY;

    /**
     * Constructor to initialize the Camera with start position, zoom, and movement boundaries.
     *
     * @param startX         Initial X position of the camera
     * @param startY         Initial Y position of the camera
     * @param initialZoom    Initial zoom level of the camera
     * @param smoothFactor   Smoothness factor for movement and zoom transitions
     * @param boundaryLimits Array containing minX, minY, maxX, maxY to define camera's movement boundaries
     */
    public Camera(float startX, float startY, float initialZoom,
                  float smoothFactor, float[] boundaryLimits)
    {
        // Initialize camera position, zoom, and target position and zoom
        this.x = startX;
        this.y = startY;
        this.zoom = initialZoom;
        this.targetX = startX;
        this.targetY = startY;
        this.targetZoom = initialZoom;

        // Set smoothness factor for camera transitions
        this.smoothFactor = smoothFactor;

        // Set camera movement boundaries
        this.minX = boundaryLimits[0];
        this.minY = boundaryLimits[1];
        this.maxX = boundaryLimits[2];
        this.maxY = boundaryLimits[3];
    }

    /**
     * Updates the camera's position smoothly towards the target position based on the smoothFactor.
     * The camera will also be constrained within the defined boundaries.
     *
     * @param deltaTime The time elapsed since the last update (for smooth frame-dependent transitions)
     */
    public void updateMovement(float deltaTime)
    {
        // Smoothly interpolate between current position and target position for X and Y
        this.x = lerp(this.x, this.targetX, this.smoothFactor * deltaTime);
        this.y = lerp(this.y, this.targetY, this.smoothFactor * deltaTime);

        // Enforce boundary restrictions on the X and Y position to prevent the camera from going out of bounds
        this.x = clamp(this.x, minX, maxX);
        this.y = clamp(this.y, minY, maxY);
    }

    /**
     * Updates the camera's zoom level smoothly towards the target zoom based on the smoothFactor.
     *
     * @param deltaTime The time elapsed since the last update (for smooth frame-dependent transitions)
     */
    public void updateZoom(float deltaTime)
    {
        // Smoothly interpolate between current zoom and target zoom
        this.zoom = lerp(this.zoom, this.targetZoom, this.smoothFactor * deltaTime);
    }

    /**
     * Applies camera transformations (translation and zoom) to the provided Graphics2D object.
     * This is typically used to offset the world rendering based on the camera's position and zoom.
     *
     * @param g The Graphics2D object used for rendering the world (tiles, sprites, etc.)
     */
    public void applyTransformations(Graphics2D g)
    {
        // Apply translation to adjust the drawing origin to the camera's position
        // The camera’s world position is negated to move the world in the opposite direction
        g.translate(-this.x, -this.y);

        // Apply scaling to zoom in/out the world based on the camera's zoom level
        g.scale(this.zoom, this.zoom);
    }

    /**
     * Sets the target position that the camera will move towards.
     * This position is updated over time to allow smooth movement.
     *
     * @param targetX The target X position for the camera
     * @param targetY The target Y position for the camera
     */
    public void setTargetPosition(float targetX, float targetY)
    {
        // Update the target position the camera should move towards
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * Sets the target zoom level that the camera will zoom towards.
     * This zoom is updated over time to allow smooth zoom transitions.
     *
     * @param targetZoom The target zoom level for the camera
     */
    public void setTargetZoom(float targetZoom)
    {
        // Update the target zoom the camera should transition towards
        this.targetZoom = targetZoom;
    }

    /**
     * Linearly interpolates between two values based on the alpha factor.
     *
     * @param start The starting value
     * @param end   The target value
     * @param alpha The interpolation factor (0.0 = start, 1.0 = end)
     * @return The interpolated value between start and end
     */
    private float lerp(float start, float end, float alpha)
    {
        return start + alpha * (end - start); // Basic linear interpolation formula
    }

    /**
     * Clamps a value to be within a specified range [min, max].
     *
     * @param value The value to clamp
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return The clamped value
     */
    private float clamp(float value, float min, float max)
    {
        // Ensure the value stays within the boundaries [min, max]
        return Math.max(min, Math.min(value, max));
    }

    // Getter methods for camera position and zoom

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getZoom()
    {
        return zoom;
    }
}
