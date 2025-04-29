package misa.systems.camera;

import misa.entities.GameObject;

/**
 * CameraFollower smoothly or instantly moves the Camera to follow a target GameObject.
 *
 * <p>
 * - In smooth mode: camera gradually approaches the target.
 * - In snap mode: camera jumps immediately to center on the target.
 * </p>
 */
@SuppressWarnings("unused")
public class CameraFollower
{
    private final Camera camera;
    private final GameObject target;
    private final float followSpeed; // Speed factor when smoothing (units per second)
    private final boolean smoothFollow;

    /**
     * Creates a new CameraFollower.
     *
     * @param camera       The Camera to control.
     * @param target       The GameObject to follow.
     * @param followSpeed  How fast to move towards the target (only relevant if smoothFollow is true).
     * @param smoothFollow Whether to use smooth interpolation or instant snapping.
     */
    public CameraFollower(Camera camera, GameObject target, float followSpeed, boolean smoothFollow)
    {
        this.camera = camera;
        this.target = target;
        this.followSpeed = followSpeed;
        this.smoothFollow = smoothFollow;
    }

    /**
     * Updates the camera position each frame.
     *
     * @param deltaTime Time elapsed since last update (in seconds).
     */
    public void update(float deltaTime)
    {
        // Target camera position to center the target
        float targetX = (float) target.getCoordinateX() - camera.getViewportUnitsWidth() / 2f;
        float targetY = (float) target.getCoordinateY() - camera.getViewportUnitsHeight() / 2f;

        if (smoothFollow)
        {
            // Move the camera a fraction toward the target based on follow speed
            float dx = targetX - camera.getX();
            float dy = targetY - camera.getY();

            camera.move(
                    dx * followSpeed * deltaTime,
                    dy * followSpeed * deltaTime
            );
        }
        else
        {
            // Instantly snap the camera to the target position
            camera.setPosition(targetX, targetY);
        }
    }
}
