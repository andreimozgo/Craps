package by.mozgo.craps.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the bet database table.
 *
 * @author Mozgo Andrei
 *
 */
public class Bet extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private long gameId;
    private byte betTypeId;
    private BigDecimal amount;
    private BigDecimal profit;
    private boolean isFirstRoll = true;
    private byte point = 0;

    public Bet() {
    }

    public Bet(byte betTypeId, BigDecimal amount) {
        this.betTypeId = betTypeId;
        this.amount = amount;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public byte getBetTypeId() {
        return betTypeId;
    }

    public void setBetTypeId(byte betTypeId) {
        this.betTypeId = betTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public boolean isFirstRoll() {
        return isFirstRoll;
    }

    public void setFirstRoll(boolean firstRoll) {
        isFirstRoll = firstRoll;
    }

    public byte getPoint() {
        return point;
    }

    public void setPoint(byte point) {
        this.point = point;
    }
}
