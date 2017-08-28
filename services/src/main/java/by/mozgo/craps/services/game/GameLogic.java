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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains game logic methods.
 *
 * @author Mozgo Andrei
 */
public class GameLogic {
    private static final Logger LOG = LogManager.getLogger();
    private static final int PASS_BET_ID = 1;
    private static final int DONT_PASS_BET_ID = 2;
    private static final int COME_BET_ID = 3;
    private static final int DONT_COME_BET_ID = 4;
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

    /**
     * Creates new bet, adds bet to the game, charges bet amount from user balance
     *
     * @param betTypeId
     * @param amount
     * @throws ServiceException
     */
    public void addBet(int betTypeId, String amount) throws ServiceException {
        Bet bet = new Bet(betTypeId, new BigDecimal(amount));
        bet.setGameId(findGameId());
        BigDecimal newUserBalance = user.getBalance().subtract(bet.getAmount());
        TransactionAssistant transaction = new TransactionAssistant();
        try {
            transaction.startTransaction();
            int betId = betService.create(bet);
            bet.setId(betId);
            newBets.add(bet);
            user.setBalance(newUserBalance);
            userService.update(user);
            transaction.commitTransaction();
        } catch (ServiceException | TransactionAssistantException e) {
            try {
                transaction.rollBackTransaction();
            } catch (TransactionAssistantException e1) {
                LOG.log(Level.ERROR, "Unable to rollback transaction. {}", e1);
            }
            throw new ServiceException("Unable to add bet.\n" + e.getMessage() + "\n", e);
        } finally {
            try {
                transaction.endTransaction();
            } catch (TransactionAssistantException e) {
                throw new ServiceException("Exception during ending transaction.\n" + e.getMessage(), e);
            }
        }
    }

    /**
     * Private access method to get Id of current game. Creates new game
     * if current doesn" exist
     *
     * @return current game Id
     * @throws ServiceException
     */
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

    /**
     * Clears played bets from game. Sets game null if game doesn't contain
     * any bet.
     */
    private void clearOldBets() {
        if (user.getGame() != null) {
            List<Bet> bets = user.getGame().getBets();
            bets.removeIf(bet -> bet.getProfit() != null);
            if (user.getGame().getBets().isEmpty()) {
                user.setGame(null);
            }
        }
    }

    /**
     * Rolls dice, calculates bets results, updates data at database
     *
     * @return RollResult with dice numbers
     * @throws ServiceException
     */
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
                            TransactionAssistant transaction = new TransactionAssistant();
                            try {
                                transaction.startTransaction();
                                betService.update(bet);
                                user.setBalance(newUserBalance);
                                userService.update(user);
                                transaction.commitTransaction();
                            } catch (ServiceException | TransactionAssistantException e) {
                                try {
                                    transaction.rollBackTransaction();
                                } catch (TransactionAssistantException e1) {
                                    LOG.log(Level.ERROR, "Unable to rollback transaction. {}", e1);
                                }
                                throw new ServiceException("Unable to add bet.\n" + e.getMessage() + "\n", e);
                            } finally {
                                try {
                                    transaction.endTransaction();
                                } catch (TransactionAssistantException e) {
                                    throw new ServiceException("Exception during ending transaction.\n" + e.getMessage(), e);
                                }

                            }
                        }
                    }
                }
            }
        }
        return new RollResult(dice1, dice2);
    }

    /**
     * Private access method to generate random dice number
     *
     * @return dice number
     */
    private int diceGenerator() {
        Random generator = new Random();
        int dice = generator.nextInt(6) + 1;
        return dice;
    }

    /**
     * Private access method to check bet
     */
    private void checkBet(Bet bet) {
        switch (bet.getBetTypeId()) {
            case PASS_BET_ID:
                if (bet.isFirstRoll()) {
                    checkPassLineFirst(bet);
                } else {
                    checkPassLineOneMore(bet);
                }
                break;
            case DONT_PASS_BET_ID:
                if (bet.isFirstRoll()) {
                    checkDoNotPassLineFirst(bet);
                } else {
                    checkDoNotPassLineOneMore(bet);
                }
                break;
            case COME_BET_ID:
                if (bet.isFirstRoll()) {
                    checkComeFirst(bet);
                } else {
                    checkComeOneMore(bet);
                }
                break;
            case DONT_COME_BET_ID:
                if (bet.isFirstRoll()) {
                    checkDoNotComeFirst(bet);
                } else {
                    checkDoNotComeOneMore(bet);
                }
                break;
        }
    }

    /**
     * Checks Pass Line bet if it is the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Pass Line bet if it isn't the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Don't Pass Line bet if it is the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Don't Pass Line bet if it isn't the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Come bet if it is the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Come bet if it isn't the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Don't Come bet if it is the first roll
     *
     * @param bet
     */
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

    /**
     * Checks Don't Come bet if it isn't the first roll
     *
     * @param bet
     */
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
