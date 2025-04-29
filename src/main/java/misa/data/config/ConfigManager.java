package misa.data.config;

import java.io.*;
import java.util.Properties;

/**
 * ConfigManager is responsible for loading, saving, and managing configuration settings
 * using a properties file ("config/config.properties" by default).
 * <p>
 * If the configuration file does not exist, it is automatically created
 * with a set of default values.
 */
@SuppressWarnings("unused")
public class ConfigManager
{
    private static final String CONFIG_FILE = "config/config.properties";

    // Stores the loaded configuration properties
    private final Properties properties;

    /**
     * Creates a new ConfigManager instance.
     * <p>
     * This automatically attempts to load the configuration file.
     * <p>
     * (Note: The constructor accepts a String parameter for future flexibility,
     * but currently always loads from CONFIG_FILE.)
     *
     * @param string Placeholder for future path customization (currently unused).
     */
    public ConfigManager(String string)
    {
        properties = new Properties();
        load();
    }

    /**
     * Loads the configuration file.
     * <p>
     * If the file does not exist, a new one with default settings is created automatically.
     *
     * @return The loaded Properties object.
     */
    public Properties load()
    {
        File file = new File(CONFIG_FILE);

        if (!file.exists())
        {
            System.out.println("Config file not found! Creating a new one with default settings...");
            setDefaults();
            save(); // Save the newly created default config
            System.out.println("Default config file created at: " + CONFIG_FILE);
            System.out.println("Please edit the config file to adjust settings.");
        }
        else
        {
            try (InputStream input = new FileInputStream(CONFIG_FILE))
            {
                properties.load(input);
                System.out.println("Config file loaded successfully.");
            }
            catch (IOException e)
            {
                System.out.println("Error loading config file: " + CONFIG_FILE);
                e.printStackTrace();
            }
        }

        return properties;
    }

    /**
     * Saves the current configuration properties back to the file.
     * <p>
     * If the directory does not exist, it is created automatically.
     */
    public void save()
    {
        try
        {
            File configFile = new File(CONFIG_FILE);
            File configDir = configFile.getParentFile();

            // Ensure the config directory exists before trying to write
            if (!configDir.exists())
            {
                if (configDir.mkdirs())
                {
                    System.out.println("Created missing directory: " + configDir.getAbsolutePath());
                }
                else
                {
                    System.out.println("Failed to create config directory.");
                    return; // Avoid trying to write into a missing directory
                }
            }

            try (OutputStream output = new FileOutputStream(CONFIG_FILE))
            {
                properties.store(output, "Game Configuration File - Edit as needed");
                System.out.println("Config file saved successfully.");
            }
        }
        catch (IOException e)
        {
            System.out.println("Error saving config file.");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a setting value as a String.
     *
     * @param key          The configuration key.
     * @param defaultValue The value to return if the key is missing.
     * @return The setting value as a String.
     */
    public String get(String key, String defaultValue)
    {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Retrieves a setting value as an integer.
     *
     * @param key          The configuration key.
     * @param defaultValue The value to return if the key is missing.
     * @return The setting value as an int.
     */
    public int getInt(String key, int defaultValue)
    {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    /**
     * Retrieves a setting value as a boolean.
     *
     * @param key          The configuration key.
     * @param defaultValue The value to return if the key is missing.
     * @return The setting value as a boolean.
     */
    public boolean getBoolean(String key, boolean defaultValue)
    {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    /**
     * Sets or updates a setting value.
     *
     * @param key   The configuration key.
     * @param value The new value.
     */
    public void set(String key, String value)
    {
        properties.setProperty(key, value);
    }

    /**
     * Sets default settings for when no configuration file exists yet.
     * <p>
     * These defaults are written into a new config file automatically.
     */
    private void setDefaults()
    {
        properties.setProperty("window_width", "800");
        properties.setProperty("window_height", "600");
        properties.setProperty("fullscreen", "false");
        properties.setProperty("volume", "0.5");
        properties.setProperty("target_fps", "60");
        properties.setProperty("target_ups", "60");
    }
}
