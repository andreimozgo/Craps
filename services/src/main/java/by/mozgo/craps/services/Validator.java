package by.mozgo.craps.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class Validator {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zА-яё])(?=.*[A-ZА-ЯЁ])(?=\\S+$).{6,}$";
    private static final String EMAIL_REGEX = "^[\\w.-_]+@\\w+\\.[.\\w]+$";
    private static final String NAME_REGEX = "^[\\p{L}\\d_]{5,}$";
    private static final String NUMBER_REGEX = "^\\d+$";
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;


    public static boolean validatePassword(String password) {
        return validate(PASSWORD_REGEX, password);
    }

    public static boolean validateEmail(String email) {
        return validate(EMAIL_REGEX, email);
    }

    public static boolean validateName(String name) {
        return validate(NAME_REGEX, name);
    }

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

    public static boolean validateMoney(String value){
        boolean result = false;
        if (validate(NUMBER_REGEX, value)) {
            int ageInt = Integer.parseInt(value);
            if (ageInt >= 0) {
                result = true;
            }
        }
        return result;    }

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
