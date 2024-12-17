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

    // Load configuration from the file
    public Properties load()
    {
        try (InputStream input = new FileInputStream(CONFIG_FILE))
        {
            properties.load(input);
        }
        catch (IOException e)
        {
            System.out.println("Config file not found, using default settings.");
            setDefaults();
            save(); // Save defaults if file doesn't exist
        }
        return null;
    }

    // Save configuration to the file
    public void save()
    {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE))
        {
            properties.store(output, "Game Configuration File");
        }
        catch (IOException e)
        {
            System.out.println("Error saving config file.");
            e.printStackTrace();
        }
    }

    // Get a setting value as String
    public String get(String key, String defaultValue)
    {
        return properties.getProperty(key, defaultValue);
    }

    // Get a setting value as int
    public int getInt(String key, int defaultValue)
    {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    // Get a setting value as boolean
    public boolean getBoolean(String key, boolean defaultValue)
    {
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    // Set a setting value
    public void set(String key, String value)
    {
        properties.setProperty(key, value);
    }

    // Set defaults if no configuration file is found
    private void setDefaults()
    {
        properties.setProperty("window_width", "800");
        properties.setProperty("window_height", "600");
        properties.setProperty("fullscreen", "false");
        properties.setProperty("volume", "0.5");
    }
}
