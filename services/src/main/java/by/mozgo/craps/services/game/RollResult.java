package by.mozgo.craps.services.game;

/**
 * Entity that contains dice values.
 *
 * @author Mozgo Andrei
 *
 */
public class RollResult {
    private byte dice1;
    private byte dice2;

    public RollResult(byte dice1, byte dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public byte getDice1() {
        return dice1;
    }

    public byte getDice2() {
        return dice2;
    }
}
