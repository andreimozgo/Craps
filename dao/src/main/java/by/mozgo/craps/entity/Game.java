package by.mozgo.craps.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class Game extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;
    private Integer userId;
    private List<Bet> bets;
    private boolean isFirstRoll = true;

    public Game(){}

    public Game(Integer userId){
        this.userId = userId;
        bets = new ArrayList<>();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
