package misa.systems.camera;

/**
 * The CameraBoundary class is responsible for enforcing movement restrictions on the camera.
 * It ensures that the camera stays within specified boundaries by clamping its position.
 * This helps to prevent the camera from moving outside the desired area, which could be useful
 * for situations like limiting the view to within the bounds of a map or environment.
 */
@SuppressWarnings("unused")
public class CameraBoundary
{
    // The minimum and maximum x and y values to define the boundaries of the camera's movement
    private final float minX, minY, maxX, maxY;

    /**
     * Constructor to initialize the CameraBoundary with specific boundary values.
     *
     * @param minX The minimum x-coordinate boundary (left boundary)
     * @param minY The minimum y-coordinate boundary (top boundary)
     * @param maxX The maximum x-coordinate boundary (right boundary)
     * @param maxY The maximum y-coordinate boundary (bottom boundary)
     */
    public CameraBoundary(float minX, float minY, float maxX, float maxY)
    {
        this.minX = minX;  // Set the left boundary of the camera
        this.minY = minY;  // Set the top boundary of the camera
        this.maxX = maxX;  // Set the right boundary of the camera
        this.maxY = maxY;  // Set the bottom boundary of the camera
    }

    /**
     * Enforces the movement boundaries for the given camera. The camera's position is clamped
     * to ensure it does not exceed the specified minimum and maximum boundaries.
     *
     * @param camera The Camera object whose position will be clamped to the boundaries
     */
    public void enforceBounds(Camera camera)
    {
        // Clamp the camera's x and y position to the defined boundaries (minX, minY, maxX, maxY)
        camera.setTargetPosition(
                clamp(camera.getX(), minX, maxX),  // Ensure the camera's x position is within bounds
                clamp(camera.getY(), minY, maxY)   // Ensure the camera's y position is within bounds
        );
    }

    /**
     * Clamps a value to be within the specified minimum and maximum range.
     * If the value is less than the minimum, it returns the minimum. If the value is greater
     * than the maximum, it returns the maximum. Otherwise, it returns the value itself.
     *
     * @param value The value to be clamped
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return The clamped value, ensuring it is between min and max
     */
    private float clamp(float value, float min, float max)
    {
        // Return the value, ensuring it is within the min and max bounds
        return Math.max(min, Math.min(value, max));
    }
}
