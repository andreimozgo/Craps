package by.mozgo.craps.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class Bet extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer gameId;
    private Integer userId;
    private Integer betTypeId;
    private BigDecimal amount;
    private BigDecimal profit;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
