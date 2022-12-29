package com.nhnacademy.gw1.parking;

public class NotAllowInLargeCar extends RuntimeException {
    public NotAllowInLargeCar() {
        super("Not Allow In Large Car");
    }
}
