package by.mozgo.craps.services.game;

import java.util.Random;

/**
 * @author Andrei Mozgo
 */
public class Dice {
    public static int diceGenerator() {
        Random generator = new Random();
        int dice = generator.nextInt(6) + 1;
        return dice;
    }
}
