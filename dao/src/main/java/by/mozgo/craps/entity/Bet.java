package by.mozgo.craps.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class Bet extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer gameId;
    private BetType betType;
    private BigDecimal amount;
    private BigDecimal profit;
    private boolean isFirstRoll = true;
    private Integer point = 0;

    public Bet(BetType betType, BigDecimal amount){
        this.betType = betType;
        this.amount = amount;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public enum BetType {
        PASS, DONTPASS, COME, DONTCOME;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

    }
}
