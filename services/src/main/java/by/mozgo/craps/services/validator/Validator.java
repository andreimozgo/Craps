package by.mozgo.craps.services.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains methods to validate attributes received from request
 *
 * @author Mozgo Andrei
 *
 */
public class Validator {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zА-яё])(?=.*[A-ZА-ЯЁ])(?=\\S+$).{6,}$";
    private static final String EMAIL_REGEX = "^[\\w.-_]+@\\w+\\.[.\\w]+$";
    private static final String NAME_REGEX = "^[\\p{L}\\d_]{5,}$";
    private static final String NUMBER_REGEX = "^\\d+$";
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;

    /**
     * Validates received password
     *
     * @param password
     * @return true if password is valid and false if password is invalid
     */
    public static boolean validatePassword(String password) {
        return validate(PASSWORD_REGEX, password);
    }

    /**
     * Validates received email
     *
     * @param email
     * @return true if email is valid and false if email is invalid
     */
    public static boolean validateEmail(String email) {
        return validate(EMAIL_REGEX, email);
    }

    /**
     * Validates received name
     *
     * @param name
     * @return true if name is valid and false if name is invalid
     */
    public static boolean validateName(String name) {
        return validate(NAME_REGEX, name);
    }

    /**
     * Validates received age
     *
     * @param age
     * @return true if age is valid and acceptable and false if isn't
     */
    public static boolean validateAge(String age) {
        boolean result = false;
        if (validate(NUMBER_REGEX, age)) {
            int ageInt = Integer.parseInt(age);
            if (ageInt >= MIN_AGE && ageInt <= MAX_AGE) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Validates received amount of money
     *
     * @param value amount of money
     * @return true if value is valid and false if isn't
     */
    public static boolean validateMoney(String value){
        boolean result = false;
        if (validate(NUMBER_REGEX, value)) {
            int ageInt = Integer.parseInt(value);
            if (ageInt >= 0) {
                result = true;
            }
        }
        return result;    }

    /**
     * Private access method that validates value with given regex.
     * Trims the value before check to reduce input errors
     *
     * @param regex regular expression
     * @param value
     * @return true if value is valid and false if isn't
     */
    private static boolean validate(String regex, String value) {
        boolean result = false;
        if (value != null) {
            value = value.trim();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            result = matcher.matches();
        }
        return result;
    }
}
