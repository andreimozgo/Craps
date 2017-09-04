package by.mozgo.craps.services.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Andrei Mozgo
 */
@RunWith(Parameterized.class)
public class ValidatorTest {
    private String password;
    private String email;
    private String name;
    private String age;
    private String money;
    private boolean expected;
    private boolean actual;

    public ValidatorTest(String password, String email, String name, String age, String money, boolean expected) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.age = age;
        this.money = money;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"qqqqQ11", "andrei.mozgo@gmail.com", "Andrei", "25", "1000", true},
                {"ййййЙ11", "andrei.mozgo@gmail.ru", "Андрей", "100", "50", true},
                {"qqqqq11", "andrei.mozgo_gmail.com", "And", "10", "-10", false},
                {"qqqqQQQ", "andrei.mozgo@gmail", "Andrei*", "twenty", "ten dollars", false}
        });
    }

    @Test
    public void testValidatePassword() {
        actual = Validator.validatePassword(this.password);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValidateEmail() {
        actual = Validator.validateEmail(this.email);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValidateName() {
        actual = Validator.validateName(this.name);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValidateAge() {
        actual = Validator.validateAge(this.age);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValidateMoney() {
        actual = Validator.validateMoney(this.money);
        Assert.assertEquals(expected, actual);
    }
}
