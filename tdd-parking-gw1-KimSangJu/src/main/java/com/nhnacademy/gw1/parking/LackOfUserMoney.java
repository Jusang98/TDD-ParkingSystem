package com.nhnacademy.gw1.parking;

public class LackOfUserMoney extends RuntimeException {
    public LackOfUserMoney() {
        super("Lack of User Money");
    }
}
