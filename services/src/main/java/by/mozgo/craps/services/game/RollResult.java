package by.mozgo.craps.services.game;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class RollResult {
    private int dice1;
    private int dice2;

    public RollResult(int dice1, int dice2){
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }
}
