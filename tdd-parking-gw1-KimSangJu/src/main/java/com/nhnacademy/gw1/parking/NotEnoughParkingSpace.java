package com.nhnacademy.gw1.parking;

public class NotEnoughParkingSpace extends RuntimeException {
    public NotEnoughParkingSpace() {
        super("Not Enough ParkingSpace");
    }
}
