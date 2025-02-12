package misa.data.config;

import java.io.*;
import java.util.Properties;

/**
 * ConfigManager is responsible for managing configuration settings using java.util.Properties.
 */
@SuppressWarnings("unused")
public class ConfigManager
{
    private static final String CONFIG_FILE = "config/config.properties";
    private final Properties properties;

    public ConfigManager(String s)
    {
        properties = new Properties();
        load();
    }

    /**
     * Loads configuration from the file. If the file is missing, it creates a new one with default settings.
     */
    public Properties load()
    {
        File file = new File(CONFIG_FILE);

        if (!file.exists())
        {
            System.out.println("Config file not found! Creating a new one with default settings...");
            setDefaults();
            save(); // Save the default config
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
     * Saves the configuration to the file, ensuring the directory exists.
     */
    public void save()
    {
        try
        {
            File configFile = new File(CONFIG_FILE);
            File configDir = configFile.getParentFile();

            // Ensure the directory exists before saving the file
            if (!configDir.exists())
            {
                if (configDir.mkdirs())
                {
                    System.out.println("Created missing directory: " + configDir.getAbsolutePath());
                }
                else
                {
                    System.out.println("Failed to create config directory.");
                    return; // Prevents writing to a non-existent directory
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
     */
    public String get(String key, String defaultValue)
    {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Retrieves a setting value as an int.
     */
    public int getInt(String key, int defaultValue)
    {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    /**
     * Retrieves a setting value as a boolean.
     */
    public boolean getBoolean(String key, boolean defaultValue)
    {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    /**
     * Sets a setting value.
     */
    public void set(String key, String value)
    {
        properties.setProperty(key, value);
    }

    /**
     * Sets default values if the config file is missing.
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
