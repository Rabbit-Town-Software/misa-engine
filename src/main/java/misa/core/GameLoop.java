package misa.core;

import misa.data.config.ConfigManager;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Properties;

/**
 * The main game loop class, responsible for updating the game state and rendering.
 */
@SuppressWarnings("unused")
public class GameLoop implements Runnable, KeyListener, MouseListener
{
    private int targetFPS;        // Target frame rate (frames per second)
    private int targetUPS;        // Target updates per second (for physics, game logic)

    private boolean running;
    private Thread gameThread;
    private final TimeSystem timeSystem;

    private double delta;
    private int frames = 0, ticks = 0;

    private final ConfigManager configManager;

    // Constructor for GameLoop
    public GameLoop(TimeSystem timeSystem)
    {
        this.timeSystem = timeSystem;
        this.running = false;

        // Initialize and load configuration
        configManager = new ConfigManager("config/config.properties");
        loadConfiguration();
    }

    /**
     * Loads configuration settings from the properties file.
     */
    private void loadConfiguration()
    {
        Properties config = configManager.load();

        // Load TARGET_FPS and TARGET_UPS, with fallbacks in case they are not set
        this.targetFPS = Integer.parseInt(config.getProperty("target_fps", "60"));
        this.targetUPS = Integer.parseInt(config.getProperty("target_ups", "60"));

        System.out.println("Loaded Configuration: ");
        System.out.println("Target FPS: " + targetFPS);
        System.out.println("Target UPS: " + targetUPS);
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
        // Render the game world (this would be specific to your rendering system)
        // Example: graphics.drawImage(...) or something similar
        System.out.println("Rendering the game world...");
    }

    // Handle input (keyboard, mouse, etc.)
    private void handleInput()
    {
        // Here you'd check for specific input actions like key presses, mouse clicks, etc.
    }

    // KeyListener and MouseListener for capturing input
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyChar());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at: (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
