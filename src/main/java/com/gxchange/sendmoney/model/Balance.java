package com.gxchange.sendmoney.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    // Constructors
    public Balance() {}

    public Balance(User user) {
        this.user = user;
        this.amount = BigDecimal.ZERO;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
