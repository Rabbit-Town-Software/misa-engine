package misa.core;

import misa.core.events.EventManager;

import misa.core.events.gameplay.entity.EntityDestroyEvent;
import misa.core.events.gameplay.entity.EntityDestroyListener;
import misa.core.events.gameplay.entity.EntitySpawnEvent;
import misa.core.events.gameplay.entity.EntitySpawnListener;

import misa.core.events.gameplay.tiled.TileEnterEvent;
import misa.core.events.gameplay.tiled.TileEnterListener;
import misa.core.events.gameplay.tiled.TileExitEvent;
import misa.core.events.gameplay.tiled.TileExitListener;

import misa.core.events.gameplay.time.TimeChangeEvent;
import misa.core.events.gameplay.time.TimeChangeListener;

import misa.core.events.input.KeyPressEvent;
import misa.core.events.input.KeyPressListener;
import misa.core.events.input.KeyReleaseEvent;
import misa.core.events.input.KeyReleaseListener;
import misa.core.events.input.MouseClickEvent;
import misa.core.events.input.MouseClickListener;
import misa.core.events.input.MouseMoveEvent;
import misa.core.events.input.MouseMoveListener;

import misa.core.events.lifecycle.GameOverEvent;
import misa.core.events.lifecycle.GameOverListener;
import misa.core.events.lifecycle.GamePauseEvent;
import misa.core.events.lifecycle.GamePauseListener;
import misa.core.events.lifecycle.GameResumeEvent;
import misa.core.events.lifecycle.GameResumeListener;
import misa.core.events.lifecycle.GameStartEvent;
import misa.core.events.lifecycle.GameStartListener;

import misa.core.events.rendering.RenderEndEvent;
import misa.core.events.rendering.RenderEndListener;
import misa.core.events.rendering.RenderStartEvent;
import misa.core.events.rendering.RenderStartListener;

import misa.data.config.ConfigManager;

import java.util.Properties;

/**
 * The main game loop class, responsible for updating the game state and rendering.
 */
@SuppressWarnings("unused")
public class GameLoop implements Runnable
{
    private int targetFPS;        // Target frame rate (frames per second)
    private int targetUPS;        // Target updates per second (for physics, game logic)

    private boolean running;
    private Thread gameThread;
    private final TimeSystem timeSystem;
    private final GameCanvas gameCanvas;

    private double delta;
    private int frames = 0, ticks = 0;

    private final ConfigManager configManager;
    private final EventManager eventManager;

    // Constructor for GameLoop
    public GameLoop(TimeSystem timeSystem, Renderer renderer)
    {
        this.eventManager = new EventManager();
        this.timeSystem = timeSystem;
        this.gameCanvas = new GameCanvas(renderer);
        this.running = false;

        // Initialize and load configuration
        configManager = new ConfigManager("config/config.properties");
        loadConfiguration();

        registerEventListeners();
    }

    /**
     * Loads configuration settings from the properties file.
     */
    private void loadConfiguration()
    {
        Properties config = configManager.load();

        if (config == null)  // Handle missing config file
        {
            System.out.println("Config file not found, using default settings.");
            this.targetFPS = 60;
            this.targetUPS = 60;
            return;
        }

        // Load TARGET_FPS and TARGET_UPS safely
        this.targetFPS = Integer.parseInt(config.getProperty("target_fps", "60"));
        this.targetUPS = Integer.parseInt(config.getProperty("target_ups", "60"));

        System.out.println("Loaded Configuration: ");
        System.out.println("Target FPS: " + targetFPS);
        System.out.println("Target UPS: " + targetUPS);
    }

    private void registerEventListeners()
    {
        System.out.println("Registering event listeners... ");

        eventManager.addListener(EntitySpawnEvent.class, new EntitySpawnListener());
        eventManager.addListener(EntityDestroyEvent.class, new EntityDestroyListener());
        eventManager.addListener(TileEnterEvent.class, new TileEnterListener());
        eventManager.addListener(TileExitEvent.class, new TileExitListener());
        eventManager.addListener(TimeChangeEvent.class, new TimeChangeListener());
        eventManager.addListener(KeyPressEvent.class, new KeyPressListener());
        eventManager.addListener(KeyReleaseEvent.class, new KeyReleaseListener());
        eventManager.addListener(MouseClickEvent.class, new MouseClickListener());
        eventManager.addListener(MouseMoveEvent.class, new MouseMoveListener());
        eventManager.addListener(GameOverEvent.class, new GameOverListener());
        eventManager.addListener(GamePauseEvent.class, new GamePauseListener());
        eventManager.addListener(GameResumeEvent.class, new GameResumeListener());
        eventManager.addListener(GameStartEvent.class, new GameStartListener());
        eventManager.addListener(RenderEndEvent.class, new RenderEndListener());
        eventManager.addListener(RenderStartEvent.class, new RenderStartListener());
    }

    // Method to start the game loop in a separate thread
    public void start()
    {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Stop the game loop
    public void stop()
    {
        running = false;
        try
        {
            gameThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        long previousTime = lastTime;
        long timer = System.currentTimeMillis();

        while (running)
        {
            long now = System.nanoTime();
            // Nanoseconds per update
            double NS_PER_UPDATE = 1000000000.0 / targetUPS;
            delta += (now - lastTime) / NS_PER_UPDATE;
            lastTime = now;

            // If enough time has passed, update and render
            while (delta >= 1)
            {
                ticks++;
                update();  // Update game logic
                delta -= 1;
            }

            frames++;
            render();  // Render the game world

            // FPS and UPS count (optional for debugging)
            if (System.currentTimeMillis() - timer >= 1000)
            {
                timer += 1000;
                System.out.println("FPS: " + frames + " | UPS: " + ticks);
                frames = 0;
                ticks = 0;
            }

            // Calculate the time taken for the frame
            long endTime = System.nanoTime();
            long frameTime = endTime - previousTime;

            // Sleep only if the frame was completed before the target time
            // Nanoseconds per frame
            double NS_PER_FRAME = 1000000000.0 / targetFPS;
            long sleepTime = (long) ((NS_PER_FRAME - frameTime) / 1000000); // Convert to milliseconds

            // Adjust the sleep time to avoid unnecessary sleep and busy-waiting
            if (sleepTime > 0)
            {
                try
                {
                    Thread.sleep(sleepTime); // Sleep for the remaining time to match target FPS
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            previousTime = System.nanoTime();
        }
    }

    // Method to handle game logic updates
    public void update()
    {
        // Handle input (keyboard, mouse, etc.)
        handleInput();

        // Update game logic (e.g., moving entities, collision detection, AI)
        timeSystem.update(0); // Update in-game time (if you have time system)
    }

    // Method to render the game world
    public void render()
    {
        gameCanvas.repaint();
    }

    // Handle input (keyboard, mouse, etc.)
    private void handleInput()
    {
        // Here you'd check for specific input actions like key presses, mouse clicks, etc.
    }

    public GameCanvas getGameCanvas()
    {
        return gameCanvas;
    }
}
