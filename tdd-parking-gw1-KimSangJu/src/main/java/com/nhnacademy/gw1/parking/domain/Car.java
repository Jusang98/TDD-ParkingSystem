package com.nhnacademy.gw1.parking.domain;

public class Car {
    private final int carNumber;
    private final String Carsize;
    private final User user;

    public Car(int carNumber, String carsize, User user) {
        this.carNumber = carNumber;
        Carsize = carsize;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public String getCarsize() {
        return Carsize;
    }
}
