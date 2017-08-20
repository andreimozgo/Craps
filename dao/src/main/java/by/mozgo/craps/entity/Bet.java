package by.mozgo.craps.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class Bet extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer gameId;
    private Integer betTypeId;
    private BigDecimal amount;
    private BigDecimal profit;
    private boolean isFirstRoll = true;
    private Integer point = 0;

    public Bet(Integer betTypeId, BigDecimal amount){
        this.betTypeId = betTypeId;
        this.amount = amount;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getBetTypeId() {
        return betTypeId;
    }

    public void setBetTypeId(Integer betTypeId) {
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
