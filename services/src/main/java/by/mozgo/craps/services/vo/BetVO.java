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
    private byte point;

    public BetVO(String betType, BigDecimal amount, BigDecimal profit, byte point) {
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

    public byte getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BetVO)) return false;

        BetVO betVO = (BetVO) o;

        if (point != betVO.point) return false;
        if (!betType.equals(betVO.betType)) return false;
        if (!amount.equals(betVO.amount)) return false;
        return profit.equals(betVO.profit);
    }

    @Override
    public int hashCode() {
        int result = betType.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + profit.hashCode();
        result = 31 * result + (int) point;
        return result;
    }

    @Override
    public String toString() {
        return "BetVO{" +
                "betType='" + betType + '\'' +
                ", amount=" + amount +
                ", profit=" + profit +
                ", point=" + point +
                '}';
    }
}
