package misa.systems.camera;

import java.awt.*;

/**
 * The CameraManager class is responsible for handling user input to control the camera's
 * movement and zoom, updating the camera state, and applying camera transformations to the
 * rendering context (Graphics2D). It ensures smooth camera control and enforces the camera's
 * movement boundaries.
 */
@SuppressWarnings("unused")
public class CameraManager
{
    // The Camera object that is being controlled and transformed
    private final Camera camera;

    // The CameraBoundary object to enforce camera movement limits
    private final CameraBoundary cameraBoundary;

    /**
     * Constructor to initialize the CameraManager with a specific camera and its boundaries.
     *
     * @param camera The Camera object that will be controlled
     * @param cameraBoundary The boundaries that restrict the camera's movement
     */
    public CameraManager(Camera camera, CameraBoundary cameraBoundary)
    {
        this.camera = camera;  // Assign the provided Camera object to the camera field
        this.cameraBoundary = cameraBoundary;  // Assign the CameraBoundary to enforce movement limits
    }

    /**
     * Processes user input to control the camera's position and zoom.
     * This method checks the input flags and adjusts the target position and zoom level accordingly.
     *
     * @param moveSpeed The speed at which the camera should move
     * @param zoomSpeed The speed at which the camera should zoom
     * @param moveLeft Flag indicating if the user wants to move the camera left
     * @param moveRight Flag indicating if the user wants to move the camera right
     * @param moveUp Flag indicating if the user wants to move the camera up
     * @param moveDown Flag indicating if the user wants to move the camera down
     * @param zoomIn Flag indicating if the user wants to zoom the camera in
     * @param zoomOut Flag indicating if the user wants to zoom the camera out
     */
    public void handleInput(float moveSpeed, float zoomSpeed,
                            boolean moveLeft, boolean moveRight,
                            boolean moveUp, boolean moveDown,
                            boolean zoomIn, boolean zoomOut)
    {
        // Move the camera left or right by adjusting the target position
        if (moveLeft) camera.setTargetPosition(camera.x - moveSpeed, camera.y);
        if (moveRight) camera.setTargetPosition(camera.x + moveSpeed, camera.y);

        // Move the camera up or down by adjusting the target position
        if (moveUp) camera.setTargetPosition(camera.x, camera.y - moveSpeed);
        if (moveDown) camera.setTargetPosition(camera.x, camera.y + moveSpeed);

        // Zoom in or out by adjusting the target zoom level
        if (zoomIn) camera.setTargetZoom(camera.zoom + zoomSpeed);
        if (zoomOut) camera.setTargetZoom(camera.zoom - zoomSpeed);
    }

    /**
     * Updates the camera's position and zoom based on the elapsed time,
     * ensuring smooth transitions. It also enforces the camera's movement boundaries.
     *
     * @param deltaTime The time elapsed since the last update (for smooth transitions)
     */
    public void update(float deltaTime)
    {
        // Update the camera's movement and zoom based on deltaTime
        camera.updateMovement(deltaTime);
        camera.updateZoom(deltaTime);

        // Enforce the camera boundaries to prevent the camera from going out of bounds
        cameraBoundary.enforceBounds(camera);
    }

    /**
     * Applies the camera's transformations (translation and zoom) to the provided Graphics2D object.
     * This is used to adjust how the world is drawn in relation to the camera's position and zoom level.
     *
     * @param g The Graphics2D object used for rendering the world (tiles, sprites, etc.)
     */
    public void applyCamera(Graphics2D g)
    {
        // Apply the camera's transformations to the Graphics2D object (translation and zoom)
        camera.applyTransformations(g);
    }
}
