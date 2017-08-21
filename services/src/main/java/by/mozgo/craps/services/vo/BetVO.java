package by.mozgo.craps.services.vo;

import java.math.BigDecimal;

/**
 * Entity that used to show to the user bets information
 *
 * @author Mozgo Andrei
 *
 */
public class BetVO {
    private String betType;
    private BigDecimal amount;
    private BigDecimal profit;
    private int point;

    public BetVO(String betType, BigDecimal amount, BigDecimal profit, int point){
        this.betType = betType;
        this.amount = amount;
        this.profit = profit;
        this.point = point;
    }

    public String getBetType() {
        return betType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public int getPoint() {
        return point;
    }
}
