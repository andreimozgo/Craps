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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RollResult)) return false;

        RollResult that = (RollResult) o;

        if (dice1 != that.dice1) return false;
        return dice2 == that.dice2;
    }

    @Override
    public int hashCode() {
        int result = (int) dice1;
        result = 31 * result + (int) dice2;
        return result;
    }

    @Override
    public String toString() {
        return "RollResult{" +
                "dice1=" + dice1 +
                ", dice2=" + dice2 +
                '}';
    }
}
