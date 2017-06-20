package by.mozgo.craps.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String email;

    private String password;

    private String username;

    private LocalDateTime createTime;

    private BigDecimal money;

    private UserRole userRole;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + email + '\'' +
                ", login='" + email + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }

    public enum UserRole {
        ADMIN, USER, BLOCKED;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

    }
}

