package by.mozgo.craps.services.dto;

import java.math.BigDecimal;

/**
 * Entity that used to show to the user bets information
 *
 * @author Mozgo Andrei
 *
 */
public class BetDto {
    private String betType;
    private BigDecimal amount;
    private BigDecimal profit;
    private byte point;

    public BetDto(String betType, BigDecimal amount, BigDecimal profit, byte point) {
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
        if (getClass() != o.getClass()) return false;

        BetDto betDto = (BetDto) o;

        if (point != betDto.point) return false;
        if (!betType.equals(betDto.betType)) return false;
        if (!amount.equals(betDto.amount)) return false;
        return profit.equals(betDto.profit);
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
        return "BetDto{" +
                "betType='" + betType + '\'' +
                ", amount=" + amount +
                ", profit=" + profit +
                ", point=" + point +
                '}';
    }
}
