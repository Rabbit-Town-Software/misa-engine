package misa.test;

import misa.core.*;
import misa.core.Renderer;
import misa.core.events.EventManager;
import misa.data.tiled2misa.TiledMap;
import misa.data.tiled2misa.TiledParser;
import misa.entities.GameObject;
import misa.systems.camera.Camera;
import misa.systems.camera.CameraBoundary;
import misa.systems.camera.CameraFollower;
import misa.systems.input.Input;

import javax.swing.*;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class TestGame
{
    public static void main(String[] args)
    {
        // --- Initialize Core Systems ---
        EventManager eventManager = new EventManager();
        GameObject.setEventManager(eventManager);

        TimeSystem timeSystem = new TimeSystem(1.0f, eventManager);

        final int pixelsPerUnit = 32; // User MUST define this manually
        final int viewportUnitsWide = 20; // Example: 640 / 32 = 20 units
        final int viewportUnitsTall = 15; // Example: 480 / 32 = 15 units

        Camera camera = new Camera(
                0f, 0f,
                viewportUnitsWide,
                viewportUnitsTall
        );

        // Placeholder, will update once we know map size
        CameraBoundary cameraBoundary = new CameraBoundary(0, 0, 8000, 8000);

        Renderer renderer = new Renderer(camera, null, pixelsPerUnit);
        GameLoop gameLoop = new GameLoop(timeSystem, renderer);

        GameLoopHolder.set(gameLoop); // Store globally for deltaTime access if needed

        // --- Load Player ---
        TestPlayer player = new TestPlayer(10, 10); // Remember: units now
        gameLoop.addGameObject(player);
        gameLoop.addStartable(player);
        gameLoop.addUpdatable(new TestPlayerMovement(player));

        // --- Camera Follow Setup ---
        CameraFollower cameraFollower = new CameraFollower(camera, player, 5.0f, true); // Smooth follow ON
        gameLoop.addUpdatable(new Updatable()
        {
            @Override
            public void update()
            {
                float deltaTime = GameLoopHolder.getDeltaTime();
                cameraFollower.update(deltaTime);

            }
        });


        // --- Load Map ---
        TiledParser parser = new TiledParser(new ArrayList<>());
        parser.setResourceBasePath("maps/");
        InputStream mapStream = TestGame.class.getClassLoader().getResourceAsStream("maps/testmap.tmx");

        if (mapStream != null)
        {
            TiledMap map = parser.loadFromInputStream(mapStream);
            renderer.setTiledMap(map);

            // After map loads, update boundary based on map size
            float mapWidthUnits = map.getWidth();    // Number of tiles (units)
            float mapHeightUnits = map.getHeight();  // Number of tiles (units)
            cameraBoundary.update(mapWidthUnits, mapHeightUnits);
        }
        else
        {
            System.err.println("Failed to load map: maps/testmap.tmx");
        }

        // --- Input Setup ---
        gameLoop.getGameCanvas().addKeyListener(new Input());

        // --- Launch ---
        JFrame frame = GameWindow.create("Test", 800, 600, false, false, gameLoop);
        frame.setVisible(true);
        gameLoop.start();
    }
}
