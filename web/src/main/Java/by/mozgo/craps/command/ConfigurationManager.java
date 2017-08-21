package by.mozgo.craps.command;

import java.util.ResourceBundle;

/**
 * Gets information from file config.properties.
 *
 * @author Mozgo Andrei
 */
public class ConfigurationManager {
    private static ResourceBundle resourceBundle;

    private ConfigurationManager() {
    }

    /**
     * Gets a string for the given key
     *
     * @param key the key for the desired string
     * @return the string for the given key
     * @see java.util.ResourceBundle#getString(String)
     */
    public static String getProperty(String key) {
        resourceBundle = ResourceBundle.getBundle("config");
        return resourceBundle.getString(key);
    }
}
