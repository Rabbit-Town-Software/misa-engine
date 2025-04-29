package misa.systems.camera;

/**
 * CameraBoundary restricts the Camera so it cannot move outside a defined world area (in world units).
 *
 * <p>
 * Useful for keeping the camera locked to a map or world bounds.
 * Boundaries can be dynamically updated if the map size changes.
 * </p>
 */
@SuppressWarnings("unused")
public class CameraBoundary
{
    private final float minX;
    private final float minY;
    private float maxX;
    private float maxY;
    private final float originalMinX;
    private final float originalMinY;

    /**
     * Creates a new CameraBoundary.
     *
     * @param minX Minimum world X coordinate.
     * @param minY Minimum world Y coordinate.
     * @param maxX Maximum world X coordinate.
     * @param maxY Maximum world Y coordinate.
     */
    public CameraBoundary(float minX, float minY, float maxX, float maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        // Save original bounds in case they need adjustment later
        this.originalMinX = minX;
        this.originalMinY = minY;
    }

    /**
     * Enforces that the Camera stays within the boundary.
     *
     * @param camera The camera to restrict.
     */
    public void enforceBounds(Camera camera)
    {
        camera.setPosition(
                clamp(camera.getX(), minX, maxX - camera.getViewportUnitsWidth()),
                clamp(camera.getY(), minY, maxY - camera.getViewportUnitsHeight())
        );
    }

    /**
     * Updates the maximum boundary values based on new world size.
     *
     * @param worldUnitsWidth New width of the world in units.
     * @param worldUnitsHeight New height of the world in units.
     */
    public void update(float worldUnitsWidth, float worldUnitsHeight)
    {
        // Adjust max bounds based on new world size
        float adjustedMaxX = originalMinX + worldUnitsWidth;
        float adjustedMaxY = originalMinY + worldUnitsHeight;

        this.maxX = Math.max(originalMinX, adjustedMaxX);
        this.maxY = Math.max(originalMinY, adjustedMaxY);
    }

    /**
     * Clamps a value between a minimum and maximum.
     *
     * @param value The value to clamp.
     * @param min Minimum allowed value.
     * @param max Maximum allowed value.
     * @return The clamped value.
     */
    private float clamp(float value, float min, float max)
    {
        return Math.max(min, Math.min(value, max));
    }

    // --- Getters ---

    /**
     * @return Minimum X world coordinate.
     */
    public float getMinX()
    {
        return minX;
    }

    /**
     * @return Minimum Y world coordinate.
     */
    public float getMinY()
    {
        return minY;
    }

    /**
     * @return Maximum X world coordinate.
     */
    public float getMaxX()
    {
        return maxX;
    }

    /**
     * @return Maximum Y world coordinate.
     */
    public float getMaxY()
    {
        return maxY;
    }
}
