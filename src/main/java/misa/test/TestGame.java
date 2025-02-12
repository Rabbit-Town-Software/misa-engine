package misa.test;

import misa.core.GameLoop;
import misa.core.Renderer;
import misa.core.TimeSystem;
import misa.systems.camera.Camera;
import misa.systems.camera.CameraBoundary;
import misa.core.events.EventManager;

import javax.swing.*;

public class TestGame
{
    public static void main(String[] args)
    {
        // Create an EventManager (even if not used now, it's required by TimeSystem)
        EventManager eventManager = new EventManager(); // No Lua scripting for now

        // Create TimeSystem with required rate and EventManager
        GameLoop gameLoop = getGameLoop(eventManager);

        // Start game loop in Swing UI thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(gameLoop.getGameCanvas()); // Attach GameCanvas
            frame.setVisible(true);

            gameLoop.start(); // Start the game loop
        });
    }

    private static GameLoop getGameLoop(EventManager eventManager) {
        TimeSystem timeSystem = new TimeSystem(1.0f, eventManager); // Example rate: 1.0f

        // Camera setup with smooth factor and boundary limits
        float[] boundaryLimits = {0, 0, 640, 480}; // Example boundary
        Camera camera = new Camera(0, 0, 1.0f, 0.1f, boundaryLimits);
        CameraBoundary cameraBoundary = new CameraBoundary(0, 0, 640, 480);

        // Create Renderer without a TiledMap (pass null for now)
        Renderer renderer = new Renderer(camera, cameraBoundary, null);

        // Create game loop
        // No Lua scripting needed
        return new GameLoop(timeSystem, renderer);
    }
}
