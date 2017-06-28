package by.mozgo.craps.services.game;

import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.entity.Game;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.BetService;
import by.mozgo.craps.services.GameService;
import by.mozgo.craps.services.impl.BetServiceImpl;
import by.mozgo.craps.services.impl.GameServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class GameLogic {
    private int dice1;
    private int dice2;
    private int sumDice;
    private boolean isGame = false;
    private User user;
    private GameService gameService = GameServiceImpl.getInstance();
    private BetService betService = BetServiceImpl.getInstance();

    private List<Bet> newBets = new ArrayList<>();

    public GameLogic (User user){
        this.user = user;
    }

    public void addBet(Bet.BetType betType, String amount) {
        if (amount != null && !amount.isEmpty()) {
            Bet bet = new Bet(betType, new BigDecimal(amount));
            bet.setGameId(getGameId());
            newBets.add(bet);
        }
    }

    private int getGameId(){
        int gameId;
        if (user.getGame() != null) {
            gameId = user.getGame().getId();
        } else {
            Game game = new Game(user.getId());
            gameId = gameService.create(game);
            user.setGame(game);
            isGame = true;
        }
        return gameId;
    }

    public RollResult roll() {
        if (user.getGame() != null) {
            isGame = true;
            if (!newBets.isEmpty()) {
                user.getGame().getBets().addAll(newBets);
            }
            if (isGame) {
                List<Bet> bets = user.getGame().getBets();
                dice1 = diceGenerator();
                dice2 = diceGenerator();
                sumDice = dice1 + dice2;
                for (Bet bet : bets) {
                    checkBet(bet);
                }
            }
        }
        return new RollResult(dice1, dice2);
    }

    private int diceGenerator() {
        Random generator = new Random();
        int dice = generator.nextInt(6) + 1;
        return dice;
    }

    private void checkBet(Bet bet) {
        switch (bet.getBetType()) {
            case PASS:
                if (bet.isFirstRoll()) {
                    checkPassLineFirst(bet);
                } else {
                    checkPassLineOneMore(bet);
                }
                break;
            case DONTPASS:
                if (bet.isFirstRoll()) {
                    checkDoNotPassLineFirst(bet);
                } else {
                    checkDoNotPassLineOneMore(bet);
                }
                break;
        }
    }

    private void checkPassLineFirst(Bet bet) {
        if (sumDice == 7 || sumDice == 11) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));

        } else if (sumDice == 2 || sumDice == 3 || sumDice == 12) {
            //player lose
            bet.setProfit(new BigDecimal(0));
        } else {
            //set Point
            bet.setPoint(sumDice);
            bet.setFirstRoll(false);
        }
    }

    private void checkPassLineOneMore(Bet bet) {
        if (sumDice == bet.getPoint()) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
        } else if (sumDice == 7) {
            //player lose
            bet.setProfit(new BigDecimal(0));
        }
    }

    private void checkDoNotPassLineFirst(Bet bet) {
        if (sumDice == 2 || sumDice == 3) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
        } else if (sumDice == 7 || sumDice == 11) {
            //player lose
            bet.setProfit(new BigDecimal(0));
        } else if (sumDice == 12) {
            //player gets his bet back
            bet.setProfit(bet.getAmount());
        } else {
            bet.setPoint(sumDice);
            bet.setFirstRoll(false);
        }
    }

    private void checkDoNotPassLineOneMore(Bet bet) {
        if (sumDice == 7) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
        } else if (sumDice == bet.getPoint()) {
            //player lose
            bet.setProfit(new BigDecimal(0));
        }
    }
}
