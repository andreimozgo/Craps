package by.mozgo.craps.services.game;

import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.entity.Game;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.BetService;
import by.mozgo.craps.services.GameService;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.BetServiceImpl;
import by.mozgo.craps.services.impl.GameServiceImpl;
import by.mozgo.craps.services.impl.UserServiceImpl;
import by.mozgo.craps.util.TransactionAssistant;
import by.mozgo.craps.util.TransactionAssistantException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class GameLogic {
    private static final int passBetId = 1;
    private static final int dontPassBetId = 2;
    private static final int comeBetId = 3;
    private static final int dontComeBetId = 4;
    private int dice1;
    private int dice2;
    private int sumDice;
    private boolean isGame = false;
    private User user;
    private List<Bet> newBets = new ArrayList<>();

    private GameService gameService = GameServiceImpl.getInstance();
    private BetService betService = BetServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    public GameLogic(User user) {
        this.user = user;
        clearOldBets();
    }

    public void addBet(int betTypeId, String amount) throws ServiceException {
        Bet bet = new Bet(betTypeId, new BigDecimal(amount));
        bet.setGameId(findGameId());
        BigDecimal newUserBalance = user.getBalance().subtract(bet.getAmount());
        try {
            TransactionAssistant.startTransaction();
        } catch (TransactionAssistantException e) {
            throw new ServiceException("Unable to add bet.\n" + e.getMessage(), e);
        }
        try {
            int betId = betService.create(bet);
            bet.setId(betId);
            newBets.add(bet);
            user.setBalance(newUserBalance);
            userService.update(user);
        } catch (ServiceException e) {
            try {
                TransactionAssistant.rollBackTransaction();
            } catch (TransactionAssistantException e1) {
                throw new ServiceException("Unable to add bet.\n" + e.getMessage() + "\n" + e1.getMessage(), e);
            }
            throw new ServiceException("Unable to add bet.\n" + e.getMessage() + "\n", e);
        }
        try {
            TransactionAssistant.endTransaction();
        } catch (TransactionAssistantException e) {
            throw new ServiceException("Unable to add bet.\n" + e.getMessage(), e);
        }
    }

    private int findGameId() throws ServiceException {
        int gameId;
        if (user.getGame() != null) {
            gameId = user.getGame().getId();
        } else {
            Game game = new Game(user.getId());
            gameId = gameService.create(game);
            game.setId(gameId);
            user.setGame(game);
            isGame = true;
        }
        return gameId;
    }

    private void clearOldBets() {
        if (user.getGame() != null) {
            List<Bet> bets = user.getGame().getBets();
            bets.removeIf(bet -> bet.getProfit() != null);

            if (user.getGame().getBets().isEmpty()) {
                user.setGame(null);
            }
        }
    }

    public RollResult roll() throws ServiceException {
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
                    if (bet.getProfit() != null) {
                        if (bet.getProfit().compareTo(new BigDecimal(0)) > 0) {
                            BigDecimal newUserBalance = user.getBalance().add(bet.getProfit());
                            try {
                                TransactionAssistant.startTransaction();
                            } catch (TransactionAssistantException e) {
                                throw new ServiceException("Game logic error.\n" + e.getMessage(), e);
                            }
                            try {
                                betService.update(bet);
                                user.setBalance(newUserBalance);
                                userService.update(user);
                            } catch (ServiceException e) {
                                try {
                                    TransactionAssistant.rollBackTransaction();
                                } catch (TransactionAssistantException e1) {
                                    throw new ServiceException("Game logic error.\n" + e.getMessage() + "\n" + e1.getMessage(), e);
                                }
                                throw new ServiceException("Game logic error.\n" + e.getMessage(), e);
                            }
                            try {
                                TransactionAssistant.endTransaction();
                            } catch (TransactionAssistantException e) {
                                throw new ServiceException("Unable to add bet.\n" + e.getMessage(), e);
                            }
                        }
                    }
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
        switch (bet.getBetTypeId()) {
            case passBetId:
                if (bet.isFirstRoll()) {
                    checkPassLineFirst(bet);
                } else {
                    checkPassLineOneMore(bet);
                }
                break;
            case dontPassBetId:
                if (bet.isFirstRoll()) {
                    checkDoNotPassLineFirst(bet);
                } else {
                    checkDoNotPassLineOneMore(bet);
                }
                break;
            case comeBetId:
                if (bet.isFirstRoll()) {
                    checkComeFirst(bet);
                } else {
                    checkComeOneMore(bet);
                }
                break;
            case dontComeBetId:
                if (bet.isFirstRoll()) {
                    checkDoNotComeFirst(bet);
                } else {
                    checkDoNotComeOneMore(bet);
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
            user.getGame().setFirstRoll(false);
        }
    }

    private void checkPassLineOneMore(Bet bet) {
        if (sumDice == bet.getPoint()) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
            user.getGame().setFirstRoll(true);
        } else if (sumDice == 7) {
            //player lose
            bet.setProfit(new BigDecimal(0));
            user.getGame().setFirstRoll(true);
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
            user.getGame().setFirstRoll(false);
        }
    }

    private void checkDoNotPassLineOneMore(Bet bet) {
        if (sumDice == 7) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
            user.getGame().setFirstRoll(true);
        } else if (sumDice == bet.getPoint()) {
            //player lose
            bet.setProfit(new BigDecimal(0));
            user.getGame().setFirstRoll(true);
        }
    }

    private void checkComeFirst(Bet bet) {
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

    private void checkComeOneMore(Bet bet) {
        if (sumDice == bet.getPoint()) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
            user.getGame().setFirstRoll(true);
        } else if (sumDice == 7) {
            //player lose
            bet.setProfit(new BigDecimal(0));
        }
    }

    private void checkDoNotComeFirst(Bet bet) {
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

    private void checkDoNotComeOneMore(Bet bet) {
        if (sumDice == 7) {
            //player win
            bet.setProfit(bet.getAmount().multiply(new BigDecimal(2)));
            user.getGame().setFirstRoll(true);
        } else if (sumDice == bet.getPoint()) {
            //player lose
            bet.setProfit(new BigDecimal(0));
        }
    }
}
