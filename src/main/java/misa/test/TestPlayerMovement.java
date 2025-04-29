package misa.test;

import misa.core.Updatable;
import misa.systems.input.Input;
import misa.core.GameLoopHolder;
import java.awt.event.KeyEvent;

public class TestPlayerMovement implements Updatable
{
    private final TestPlayer player;
    private String lastDirection = "South";

    public TestPlayerMovement(TestPlayer player)
    {
        this.player = player;
    }

    @Override
    public void update()
    {
        float dt = GameLoopHolder.getDeltaTime();       // e.g. 0.016s at 60FPS
        double dx = 0, dy = 0;

        // 1) Read raw input
        if (Input.isKeyPressed(KeyEvent.VK_W)) dy -= 1;
        if (Input.isKeyPressed(KeyEvent.VK_S)) dy += 1;
        if (Input.isKeyPressed(KeyEvent.VK_A)) dx -= 1;
        if (Input.isKeyPressed(KeyEvent.VK_D)) dx += 1;

        // 2) If there is input, normalize to keep diagonal speed correct
        if (dx != 0 || dy != 0)
        {
            double len = Math.sqrt(dx*dx + dy*dy);
            dx /= len;
            dy /= len;

            // 3) Apply movement: speed (units/sec) * dt (sec/frame)
            double moveX = dx * player.speed * dt;
            double moveY = dy * player.speed * dt;

            // 4) Update position in one place
            double newX = player.getCoordinateX() + moveX;
            double newY = player.getCoordinateY() + moveY;
            player.setPosition(newX, newY);

            // 5) Play walking animation based on raw dx/dy
            if (Math.abs(dx) > Math.abs(dy))
            {
                lastDirection = (dx > 0 ? "East" : "West");
            }
            else
            {
                lastDirection = (dy > 0 ? "South" : "North");
            }
            String walkAnim = "Walk" + lastDirection;
            if (player.playerAnimator.isPlaying(walkAnim))
                player.playerAnimator.play(walkAnim);
        }
        else
        {
            // No input → idle
            String idleAnim = "Idle" + lastDirection;
            if (player.playerAnimator.isPlaying(idleAnim))
                player.playerAnimator.play(idleAnim);
        }
    }
}
