package by.mozgo.craps.command;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static ResourceBundle resourceBundle;

    // this class gets information from file messages.properties
    private MessageManager() {
    }

    public static String getProperty(String key) {
        resourceBundle = ResourceBundle.getBundle("i18n/jsp", Locale.ENGLISH);
        return resourceBundle.getString(key);
    }

    public static String getProperty(String key, Locale locale) {
        resourceBundle = ResourceBundle.getBundle("i18n/jsp", locale);
        return resourceBundle.getString(key);
    }
}
