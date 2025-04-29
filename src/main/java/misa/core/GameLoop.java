package misa.core;

import misa.core.events.*;
import misa.core.events.gameplay.entity.*;
import misa.core.events.gameplay.tiled.*;
import misa.core.events.gameplay.time.*;
import misa.core.events.input.*;
import misa.core.events.lifecycle.*;
import misa.core.events.rendering.*;
import misa.data.config.ConfigManager;
import misa.entities.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * GameLoop handles the main game cycle: updating and rendering at a target rate.
 * <p>
 * It manages timing, frame control, and coordination of the update/render process,
 * while also managing the Event system and configuration settings.
 */
@SuppressWarnings("unused")
public class GameLoop implements Runnable
{
    // Timing and configuration variables
    private int targetFPS;
    private int targetUPS;
    private boolean fullscreen;
    private boolean undecorated;
    private int windowWidth;
    private int windowHeight;

    // Main loop control
    private boolean running;
    private Thread gameThread;

    // Core systems
    private final TimeSystem timeSystem;
    private final GameCanvas gameCanvas;
    private final Renderer renderer;

    // Internal counters for timing
    private double delta;
    private int frames;
    private int ticks;
    private float deltaTime;

    // Event system and configuration manager
    private final ConfigManager configManager;
    private final EventManager eventManager;

    // Lists of objects that need starting and updating
    private final List<Startable> startables;
    private final List<Updatable> updatables;

    /**
     * Creates a new GameLoop.
     *
     * @param timeSystem The TimeSystem instance to manage time-based updates.
     * @param renderer   The Renderer responsible for drawing game objects.
     */
    public GameLoop(TimeSystem timeSystem, Renderer renderer)
    {
        this.eventManager = new EventManager();
        GameObject.setEventManager(eventManager); // Make sure GameObjects know about the EventManager

        this.timeSystem = timeSystem;
        this.renderer = renderer;
        this.gameCanvas = new GameCanvas(renderer);
        this.running = false;
        this.startables = new ArrayList<>();
        this.updatables = new ArrayList<>();

        this.configManager = new ConfigManager("config/config.properties");
        loadConfiguration(); // Load settings like FPS, screen size
        registerEventListeners(); // Wire up core event listeners
    }

    /**
     * Starts the game loop in a new thread.
     */
    public void start()
    {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Stops the game loop and waits for the thread to finish.
     */
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

    /**
     * Starts all Startable objects before entering the main loop.
     */
    private void startAll()
    {
        for (Startable startable : startables)
        {
            startable.start();
        }
    }

    /**
     * Main game loop logic.
     * <p>
     * Handles updates and renders at the configured rates.
     */
    @Override
    public void run()
    {
        startAll();

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while (running)
        {
            long now = System.nanoTime();
            deltaTime = (now - lastTime) / 1_000_000_000.0f; // Time since last frame
            double nsPerUpdate = 1_000_000_000.0 / targetUPS;
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            // Update the game state if enough time has passed
            while (delta >= 1)
            {
                ticks++;
                update();
                delta--;
            }

            frames++;
            render();

            // Every second, print FPS and UPS
            if (System.currentTimeMillis() - timer >= 1000)
            {
                timer += 1000;
                System.out.println("FPS: " + frames + " | UPS: " + ticks);
                frames = 0;
                ticks = 0;
            }

            // Frame rate control: sleep to avoid running too fast
            long frameTime = System.nanoTime() - now;
            long nsPerFrame = (long) (1_000_000_000.0 / targetFPS);
            long sleepMs = (nsPerFrame - frameTime) / 1_000_000;
            if (sleepMs > 0)
            {
                try
                {
                    Thread.sleep(sleepMs);
                }
                catch (InterruptedException ignored) {}
            }
        }
    }

    /**
     * Updates all registered Updatable objects and the TimeSystem.
     */
    private void update()
    {
        timeSystem.update(deltaTime);

        for (Updatable updatable : updatables)
        {
            updatable.update();
        }
    }

    /**
     * Triggers a repaint of the game canvas.
     */
    private void render()
    {
        gameCanvas.repaint();
    }

    /**
     * Loads game configuration from a properties file.
     */
    private void loadConfiguration()
    {
        Properties config = configManager.load();
        if (config == null)
        {
            targetFPS = 60;
            targetUPS = 60;
            windowWidth = 800;
            windowHeight = 600;
            fullscreen = false;
            undecorated = false;
            return;
        }

        targetFPS = Integer.parseInt(config.getProperty("target_fps", "60"));
        targetUPS = Integer.parseInt(config.getProperty("target_ups", "60"));
        windowWidth = Integer.parseInt(config.getProperty("window_width", "800"));
        windowHeight = Integer.parseInt(config.getProperty("window_height", "600"));
        fullscreen = Boolean.parseBoolean(config.getProperty("fullscreen", "false"));
        undecorated = Boolean.parseBoolean(config.getProperty("undecorated", "false"));
    }

    /**
     * Registers default event listeners for gameplay, input, and lifecycle events.
     */
    private void registerEventListeners()
    {
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

    /**
     * Adds a Startable object to be started when the game begins.
     *
     * @param startable Object that implements Startable.
     */
    public void addStartable(Startable startable)
    {
        startables.add(startable);
    }

    /**
     * Adds an Updatable object to be updated every tick.
     *
     * @param updatable Object that implements Updatable.
     */
    public void addUpdatable(Updatable updatable)
    {
        updatables.add(updatable);
    }

    /**
     * Adds a GameObject to the Renderer for drawing.
     *
     * @param gameObject The GameObject to add.
     */
    public void addGameObject(GameObject gameObject)
    {
        renderer.addGameObject(gameObject);
    }

    /**
     * Gets the time passed since the last frame.
     *
     * @return Delta time in seconds.
     */
    public float getDeltaTime()
    {
        return deltaTime;
    }

    /**
     * Gets the GameCanvas used for rendering.
     *
     * @return The GameCanvas.
     */
    public GameCanvas getGameCanvas()
    {
        return gameCanvas;
    }

    /**
     * Gets the Renderer responsible for drawing.
     *
     * @return The Renderer.
     */
    public Renderer getRenderer()
    {
        return renderer;
    }

    /**
     * Gets the width of the game window.
     *
     * @return Width in pixels.
     */
    public int getWindowWidth()
    {
        return windowWidth;
    }

    /**
     * Gets the height of the game window.
     *
     * @return Height in pixels.
     */
    public int getWindowHeight()
    {
        return windowHeight;
    }

    /**
     * Checks if the window is set to fullscreen.
     *
     * @return True if fullscreen.
     */
    public boolean isFullscreen()
    {
        return fullscreen;
    }

    /**
     * Checks if the window is undecorated (no borders).
     *
     * @return True if undecorated.
     */
    public boolean isUndecorated()
    {
        return undecorated;
    }
}
