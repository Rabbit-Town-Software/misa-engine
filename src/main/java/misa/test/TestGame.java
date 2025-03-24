package misa.test;

import misa.core.GameLoop;
import misa.core.GameWindow;
import misa.core.Renderer;
import misa.core.TimeSystem;
import misa.entities.GameObject;
import misa.systems.camera.Camera;
import misa.systems.camera.CameraBoundary;
import misa.core.events.EventManager;
import misa.entities.Player;

import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class TestGame
{
    public static void main(String[] args)
    {
        EventManager eventManager = new EventManager();
        GameObject.setEventManager(eventManager);
        GameLoop gameLoop = getGameLoop(eventManager);

        GameWindow.launchGame(
                "Test Game",
                gameLoop.getWindowWidth(),
                gameLoop.getWindowHeight(),
                gameLoop.isFullscreen(),
                gameLoop.isUndecorated(),
                gameLoop
        );
    }

    private static GameLoop getGameLoop(EventManager eventManager)
    {
        TimeSystem timeSystem = new TimeSystem(1.0f, eventManager); // Example rate: 1.0f

        Player player = new Player(
                100,
                100,
                true,
                true,
                null
        );

        player.setShouldLoop(true);
        player.setShouldAnimate(true);

        // Load test animations (Idle)
        BufferedImage[] girlIdleEast = player.loadAnimations(new String[]
                {
                        "test_assets/girlidle/idleEast/girlIdle008.png",
                        "test_assets/girlidle/idleEast/girlIdle009.png",
                        "test_assets/girlidle/idleEast/girlIdle010.png",
                        "test_assets/girlidle/idleEast/girlIdle011.png",
                });
        BufferedImage[] girlIdleNorth = player.loadAnimations(new String[]
                {
                        "test_assets/girlidle/idleNorth/girlIdle012.png",
                        "test_assets/girlidle/idleNorth/girlIdle013.png",
                        "test_assets/girlidle/idleNorth/girlIdle014.png",
                        "test_assets/girlidle/idleNorth/girlIdle015.png",
                });
        BufferedImage[] girlIdleSouth = player.loadAnimations(new String[]
                {
                        "test_assets/girlidle/idleSouth/girlIdle000.png",
                        "test_assets/girlidle/idleSouth/girlIdle001.png",
                        "test_assets/girlidle/idleSouth/girlIdle002.png",
                        "test_assets/girlidle/idleSouth/girlIdle003.png",
                });
        BufferedImage[] girlIdleWest = player.loadAnimations(new String[]
                {
                        "test_assets/girlidle/idleWest/girlIdle004.png",
                        "test_assets/girlidle/idleWest/girlIdle005.png",
                        "test_assets/girlidle/idleWest/girlIdle006.png",
                        "test_assets/girlidle/idleWest/girlIdle007.png",
                });

        player.setAnimationFrames(girlIdleSouth);

        // Camera setup with smooth factor and boundary limits
        float[] boundaryLimits = {0, 0, 640, 480}; // Example boundary
        Camera camera = new Camera(0, 0, 1.0f, 0.0f, boundaryLimits);
        CameraBoundary cameraBoundary = new CameraBoundary(0, 0, 640, 480);

        // Create Renderer without a TiledMap (pass null for now)
        Renderer renderer = new Renderer(camera, cameraBoundary, null);
        renderer.addGameObject(player);

        // Create game loop
        return new GameLoop(timeSystem, renderer);
    }
}
