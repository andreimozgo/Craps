package by.mozgo.craps.command;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Gets information from file messages.properties
 *
 * @author Mozgo Andrei
 */
public class MessageManager {
    private static ResourceBundle resourceBundle;

    private MessageManager() {
    }

    /**
     * Gets a string for the given key and locale
     *
     * @param key the key for the desired string
     * @param locale
     * @return the string for the given key
     * @see java.util.ResourceBundle#getBundle(String, Locale)
     * @see java.util.ResourceBundle#getString(String)
     */
    public static String getProperty(String key, Locale locale) {
        resourceBundle = ResourceBundle.getBundle("i18n/jsp", locale);
        return resourceBundle.getString(key);
    }
}
