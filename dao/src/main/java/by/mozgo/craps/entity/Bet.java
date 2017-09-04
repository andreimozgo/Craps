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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;

        if (gameId != bet.gameId) return false;
        if (betTypeId != bet.betTypeId) return false;
        if (isFirstRoll != bet.isFirstRoll) return false;
        if (point != bet.point) return false;
        if (amount != null ? !amount.equals(bet.amount) : bet.amount != null) return false;
        return profit != null ? profit.equals(bet.profit) : bet.profit == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (int) betTypeId;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        result = 31 * result + (isFirstRoll ? 1 : 0);
        result = 31 * result + (int) point;
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "gameId=" + gameId +
                ", betTypeId=" + betTypeId +
                ", amount=" + amount +
                ", profit=" + profit +
                ", isFirstRoll=" + isFirstRoll +
                ", point=" + point +
                "} " + super.toString();
    }
}
