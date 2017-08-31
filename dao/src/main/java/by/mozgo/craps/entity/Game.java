package by.mozgo.craps.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the game database table.
 *
 * @author Mozgo Andrei
 *
 */
public class Game extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;
    private long userId;
    private List<Bet> bets;
    private boolean isFirstRoll = true;

    public Game(){}

    public Game(long userId) {
        this.userId = userId;
        bets = new ArrayList<>();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public boolean isFirstRoll() {
        return isFirstRoll;
    }

    public void setFirstRoll(boolean firstRoll) {
        isFirstRoll = firstRoll;
    }
}
