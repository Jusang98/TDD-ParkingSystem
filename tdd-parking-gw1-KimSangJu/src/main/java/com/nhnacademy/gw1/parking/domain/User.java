package com.nhnacademy.gw1.parking.domain;

public class User {
    private final String userId;
    private long amount;

    private final String payco;

    public User(String userId, long amount, String payco) {
        this.userId = userId;
        this.amount = amount;
        this.payco = payco;
    }

    public long getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getPayco() {
        return payco;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
