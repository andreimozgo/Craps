package by.mozgo.craps.services.suite;

import by.mozgo.craps.services.locale.LocaleLogicTest;
import by.mozgo.craps.services.validator.ValidatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Andrei Mozgo
 */

@Suite.SuiteClasses({LocaleLogicTest.class, ValidatorTest.class})
@RunWith(Suite.class)
public class SuiteTest {
}
