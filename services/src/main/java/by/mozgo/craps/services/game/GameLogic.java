package by.mozgo.craps.services.game;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class GameLogic {
    private static Boolean needMenu = true;
    private static int playerMoney = 100;
    private static int passLineBet;
    private static int doNotPassLineBet;
    private static int dice1;
    private static int dice2;
    private static int point = 0;
    private static boolean isFirstRoll = true;

    private static void checkPassLineFirst(int dice1, int dice2) {
        int sumDice = dice1 + dice2;
        if (sumDice == 7 || sumDice == 11) {
            System.out.println("Your Pass line bet won $" + passLineBet * 2);
            playerMoney += passLineBet * 2;
            System.out.println("You have $" + playerMoney);
            passLineBet = 0;
        } else if (sumDice == 2 || sumDice == 3 || sumDice == 12) {
            System.out.println("Your Pass line bet lost $" + passLineBet);
            System.out.println("You have $" + playerMoney);
            passLineBet = 0;
        } else {
            point = sumDice;
            isFirstRoll = false;
        }
    }

    private static void checkPassLineOneMore(int dice1, int dice2) {
        int sumDice = dice1 + dice2;
        if (sumDice == point) {
            System.out.println("Your Pass line bet won $" + passLineBet * 2);
            playerMoney += passLineBet * 2;
            System.out.println("You have $" + playerMoney);
            passLineBet = 0;
            point = 0;
            isFirstRoll = true;
        } else if (sumDice == 7) {
            System.out.println("Your Pass line bet lost $" + passLineBet);
            System.out.println("You have $" + playerMoney);
            passLineBet = 0;
            point = 0;
            isFirstRoll = true;
        }
    }

    private static void checkDoNotPassLineFirst(int dice1, int dice2) {
        int sumDice = dice1 + dice2;
        if (sumDice == 2 || sumDice == 3) {
            System.out.println("Your Don't Pass line bet won $" + doNotPassLineBet * 2);
            playerMoney += doNotPassLineBet * 2;
            System.out.println("You have $" + playerMoney);
            doNotPassLineBet = 0;
        } else if (sumDice == 7 || sumDice == 11) {
            System.out.println("Your Don't Pass line bet lost $" + doNotPassLineBet);
            System.out.println("You have $" + playerMoney);
            doNotPassLineBet = 0;
        } else if (sumDice == 12) {
            System.out.println("Your Don't Pass line bet won $" + doNotPassLineBet);
            playerMoney += doNotPassLineBet;
            System.out.println("You have $" + playerMoney);
            doNotPassLineBet = 0;
        } else {
            point = sumDice;
            isFirstRoll = false;
        }
    }

    private static void checkDoNotPassLineOneMore(int dice1, int dice2) {
        int sumDice = dice1 + dice2;
        if (sumDice == 7) {
            System.out.println("Your Don't Pass line bet won $" + doNotPassLineBet * 2);
            playerMoney += doNotPassLineBet * 2;
            System.out.println("You have $" + playerMoney);
            doNotPassLineBet = 0;
            point = 0;
            isFirstRoll = true;
        } else if (sumDice == point) {
            System.out.println("Your Don't Pass line bet lost $" + doNotPassLineBet);
            System.out.println("You have $" + playerMoney);
            doNotPassLineBet = 0;
            point = 0;
            isFirstRoll = true;
        }
    }

    private void roll() {
        if (passLineBet > 0 || doNotPassLineBet > 0) {
            dice1 = Dice.diceGenerator();
            dice2 = Dice.diceGenerator();
            System.out.println(dice1 + " " + dice2);
            if (isFirstRoll) {
                if (passLineBet > 0) {
                    checkPassLineFirst(dice1, dice2);
                }
                if (doNotPassLineBet > 0) {
                    checkDoNotPassLineFirst(dice1, dice2);
                }

            } else {
                if (passLineBet > 0) {
                    checkPassLineOneMore(dice1, dice2);
                }
                if (doNotPassLineBet > 0) {
                    checkDoNotPassLineOneMore(dice1, dice2);
                }
            }
        } else {
            System.out.println("Please make your bet");
        }
    }
}
