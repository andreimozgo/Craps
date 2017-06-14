package by.mozgo.craps.command;

import java.util.Locale;

/**
 * Contain supported locales and methods
 */
public class LocaleLogic {

    /**
     * return locale object according to locale string/ If not support - return
     * default locale
     *
     * @param localeStr
     * @return
     */
    public static Locale getLocaleByString(String localeStr) {
        SupportedLocale localeEnum;
        try {
            localeEnum = SupportedLocale.valueOf(localeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            localeEnum = SupportedLocale.DEFAULT;
        }
        return localeEnum.getLocale();
    }

    /**
     * supporteed locales
     */
    enum SupportedLocale {
        EN(Locale.ENGLISH), RU(new Locale("ru")), DEFAULT(Locale.ENGLISH);
        private Locale locale;

        SupportedLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }
}
