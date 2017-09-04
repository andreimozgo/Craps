package by.mozgo.craps.services.locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Mozgo Andrei
 *
 */
@RunWith(Parameterized.class)
public class LocaleLogicTest {
    private String inputString;
    private Locale expectedLocale;

    public LocaleLogicTest(String inputString, String expected) {
        this.inputString = inputString;
        this.expectedLocale = new Locale(expected);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"ru", "ru"},
                {"en", "en"},
                {"fr", "en"}
        });
    }

    @Test
    public void testGetLocaleByString() {
        Locale gotLocale = LocaleLogic.getLocaleByString(this.inputString);
        Assert.assertEquals(expectedLocale, gotLocale);
    }
}
