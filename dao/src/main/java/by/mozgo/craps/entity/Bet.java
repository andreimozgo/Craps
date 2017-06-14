package by.mozgo.craps.entity;

import java.math.BigDecimal;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class Bet {
    private Integer id;
    private Integer gameId;
    private Integer turnId;
    private Integer userId;
    private Integer betTypeId;
    private BigDecimal amount;
    private BigDecimal result;
}
