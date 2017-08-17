package by.mozgo.craps.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum UserRole {
        ADMIN, USER, BLOCKED;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

    }
    private String email;
    private String password;
    private String username;
    private LocalDateTime createTime;
    private BigDecimal balance;
    private UserRole userRole;
    private Game game;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", name='" + email + '\'' +
                ", login='" + email + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }


}

